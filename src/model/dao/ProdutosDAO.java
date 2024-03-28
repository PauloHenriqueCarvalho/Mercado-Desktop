/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import com.formdev.flatlaf.util.ScaledImageIcon;
import conexao.Conexao;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.bean.Estoque;
import model.bean.ModelItem;
import model.bean.Produto;

/**
 *
 * @author Senai
 */
public class ProdutosDAO {

    public boolean verificarProduto(String nome) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement("select * from produto where nome = ?");
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                conexao.close();
                return true;
            } else {
                ps.close();
                conexao.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void create(Produto p) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conexao.prepareStatement("INSERT INTO produto (nome, categoria, preco, desconto, descontoPorcentagem, precoFinal, imagem) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getCategoria());
            stmt.setFloat(3, p.getPreco());
            stmt.setInt(4, p.getDesconto());
            stmt.setFloat(5, p.getDescontoPorcentagem());
            stmt.setFloat(6, p.getPrecoFinal());
            stmt.setBlob(7, p.getIcon());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Produto readProduto(int idProduto) {

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM produto where idproduto = ?");
            stmt.setInt(1, idProduto);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Produto p = new Produto();
                p.setNome(rs.getString("nome"));
                p.setCategoria(rs.getInt("categoria"));
                p.setPreco(rs.getFloat("preco"));
                p.setDesconto(rs.getInt("desconto"));
                p.setDescontoPorcentagem(rs.getFloat("descontoPorcentagem"));
                p.setPrecoFinal(rs.getFloat("precoFinal"));

                rs.close();
                stmt.close();
                conexao.close();
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;

    }

    public ImageIcon getImagemDoBancoDeDados(int idProduto) {
        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement("SELECT imagem FROM produto WHERE idproduto = ?")) {

            stmt.setInt(1, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Blob blob = rs.getBlob("imagem");
                    if (blob != null && blob.length() > 0) {
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        Image imagem = ImageIO.read(new ByteArrayInputStream(imageBytes));
                        return new ImageIcon(imagem);
                    } else {
                        // Se a imagem for nula, retorne a imagem padrão
                        return new ImageIcon(getClass().getResource("/images/carregar.png"));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Produto> read() {
        List<Produto> produtos = new ArrayList();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setNome(rs.getString("nome"));
                p.setCategoria(rs.getInt("categoria"));
                p.setPreco(rs.getFloat("preco"));
                p.setDesconto(rs.getInt("desconto"));
                p.setDescontoPorcentagem(rs.getFloat("descontoPorcentagem"));
                p.setPrecoFinal(rs.getFloat("precoFinal"));
                produtos.add(p);

            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public List<ModelItem> readItens(int idCategoria) {
        List<ModelItem> produtos = new ArrayList<>();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM produto where categoria = ?");
            stmt.setInt(1, idCategoria);
            rs = stmt.executeQuery();
            Image originalImage = null;
            while (rs.next()) {
                Blob blob = rs.getBlob("imagem");
                if (blob != null && blob.getBinaryStream() != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    byte[] imageBytes = buffer.toByteArray();

                    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
                    originalImage = ImageIO.read(imageStream);
                } else {
                    originalImage = null;
                }

                Produto p = new Produto();
                ModelItem item = new ModelItem();
                if (originalImage != null) {
                    Image scaledImage = originalImage.getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    item.setImage(imageIcon);
                } else {
                    ImageIcon defaultImageIcon = new ImageIcon(getClass().getResource("/images/carregar.png"));
                    Image scaledImage = defaultImageIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    item.setImage(scaledImageIcon);
                }

                item.setPrice(rs.getFloat("precoFinal"));
                item.setItemID(rs.getInt("idProduto"));
                item.setItemName(rs.getString("nome"));

                stmt = conexao.prepareStatement("SELECT nome FROM categoria WHERE idCategoria = ?");
                stmt.setInt(1, rs.getInt("categoria"));
                ResultSet categoriaRs = stmt.executeQuery();
                if (categoriaRs.next()) {
                    String categoriaNome = categoriaRs.getString("nome");
                    item.setBrandName(categoriaNome);
                }

                item.setDescription(2);
                produtos.add(item);
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public List<ModelItem> readEstoque(int idCategoria) {
        List<ModelItem> produtos = new ArrayList<>();
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            PreparedStatement stmt2 = null;
            stmt2 = conexao.prepareStatement("SELECT * FROM estoque");
            rs = stmt2.executeQuery();
            while (rs.next()) {
                Estoque e = new Estoque();
                e.setIdEstoque(rs.getInt("idEstoque"));
                e.setProduto(rs.getInt("produto"));
                e.setQuantidade(rs.getInt("quantidade"));
                e.setDataAtualizacao(rs.getTimestamp("dataAtualizacaoEstoque"));

                stmt = conexao.prepareStatement("SELECT * FROM produto WHERE idProduto = ? AND categoria = ?");
                stmt.setInt(1, e.getProduto());
                stmt.setInt(2, idCategoria);

                ResultSet rsProduto = stmt.executeQuery();
                Image originalImage = null;
                while (rsProduto.next()) {
                    Blob blob = rsProduto.getBlob("imagem");
                    if (blob != null && blob.getBinaryStream() != null) {
                        InputStream inputStream = blob.getBinaryStream();
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        int nRead;
                        byte[] data = new byte[1024];
                        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                        }
                        buffer.flush();
                        byte[] imageBytes = buffer.toByteArray();

                        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
                        originalImage = ImageIO.read(imageStream);
                    } else {
                        originalImage = null;
                    }

                    ModelItem item = new ModelItem();
                    if (originalImage != null) {
                        Image scaledImage = originalImage.getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                        ImageIcon imageIcon = new ImageIcon(scaledImage);
                        item.setImage(imageIcon);
                    } else {
                        ImageIcon defaultImageIcon = new ImageIcon(getClass().getResource("/images/carregar.png"));
                        Image scaledImage = defaultImageIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                        item.setImage(scaledImageIcon);
                    }
                    float precoProduto = rsProduto.getFloat("preco");
                    item.setPrice(precoProduto);
                    item.setItemID(e.getIdEstoque());
                    item.setItemName(rsProduto.getString("nome"));

                    item.setDescription(e.getQuantidade());
                    produtos.add(item);
                }
                rsProduto.close();
                stmt.close();
            }

            rs.close();
            stmt2.close();
            conexao.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void update(Produto p) {

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conexao.prepareStatement("UPDATE produto SET nome = ?, categoria = ?, preco = ?, desconto = ?, descontoPorcentagem = ?, precoFinal = ? WHERE idProduto = ?");
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getCategoria());
            stmt.setFloat(3, p.getPreco());
            stmt.setInt(4, p.getDesconto());
            stmt.setFloat(5, p.getDescontoPorcentagem());
            stmt.setFloat(6, p.getPrecoFinal());
            stmt.setInt(7, p.getIdProduto());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            PreparedStatement stmt1 = null;
            stmt1 = conexao.prepareStatement("DELETE FROM estoque WHERE produto = ?");
            stmt1.setInt(1, id);
            stmt1.executeUpdate();
            stmt1.close();

            stmt = conexao.prepareStatement("DELETE FROM produto WHERE idProduto = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ModelItem> readItensPromocao() {
        List<ModelItem> produtos = new ArrayList<>();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM produto where desconto != 0");
            rs = stmt.executeQuery();
            Image originalImage = null;
            while (rs.next()) {
                Blob blob = rs.getBlob("imagem");
                if (blob != null && blob.getBinaryStream() != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    byte[] imageBytes = buffer.toByteArray();

                    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
                    originalImage = ImageIO.read(imageStream);
                } else {
                    originalImage = null;
                }

                Produto p = new Produto();
                ModelItem item = new ModelItem();
                if (originalImage != null) {
                    Image scaledImage = originalImage.getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    item.setImage(imageIcon);
                } else {
                    ImageIcon defaultImageIcon = new ImageIcon(getClass().getResource("/images/carregar.png"));
                    Image scaledImage = defaultImageIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    item.setImage(scaledImageIcon);
                }

                item.setPrice(rs.getFloat("precoFinal"));
                item.setItemID(rs.getInt("idProduto"));
                item.setItemName(rs.getString("nome"));

                stmt = conexao.prepareStatement("SELECT nome FROM categoria WHERE idCategoria = ?");
                stmt.setInt(1, rs.getInt("categoria"));
                ResultSet categoriaRs = stmt.executeQuery();
                if (categoriaRs.next()) {
                    String categoriaNome = categoriaRs.getString("nome");
                    item.setBrandName(categoriaNome);
                }

                item.setDescription(2);
                produtos.add(item);
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return produtos;
    }
}
