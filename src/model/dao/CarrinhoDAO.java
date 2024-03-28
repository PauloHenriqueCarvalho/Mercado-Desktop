/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

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
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.bean.ModelItem;

/**
 *
 * @author Senai
 */
public class CarrinhoDAO {

    private int obterIdProdutoPorNome(String nomeProduto) throws SQLException {
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement("SELECT idProduto FROM produto WHERE nome = ?");
        stmt.setString(1, nomeProduto);
        ResultSet rs = stmt.executeQuery();

        int idProduto = -1;
        if (rs.next()) {
            idProduto = rs.getInt("idProduto");
        }

        rs.close();
        stmt.close();
        conn.close();

        return idProduto;
    }

    public boolean verificarCarrinho(int idUsuario) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement(" select * from carrinho where idCarrinho = ?");
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Ja tem carrinho");
                ps.close();
                conexao.close();
                return true;
            } else {
                System.out.println("Nao tem carrinho");
                ps.close();
                conexao.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void criarCarrinho(int idUsuario) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement("insert into carrinho(usuario) values (?)");
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            System.out.println("Carrinho criado");
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ModelItem> readCarrinho(int idCarrinho) {
        List<ModelItem> itens = new ArrayList<>();
        System.out.println("ID DO CARRINHO " + idCarrinho);
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT produto.idProduto, produto.nome, produto.precoFinal, produto.imagem FROM produto "
                    + "INNER JOIN produto_carrinho ON produto.idProduto = produto_carrinho.produto "
                    + "WHERE produto_carrinho.carrinho = ?");
            stmt.setInt(1, idCarrinho);
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

                itens.add(item);
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return itens;
    }

    public boolean verificarCarrinhoVazio(int idUsuario) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement("SELECT COUNT(*) AS total FROM produto_carrinho WHERE carrinho = ?");
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalProdutos = rs.getInt("total");
                return totalProdutos == 0;
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
