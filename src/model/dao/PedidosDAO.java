/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import conexao.Conexao;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Pedido;
import model.bean.Usuario;

/**
 *
 * @author Senai
 */
public class PedidosDAO {

    public void create(Pedido p) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conexao.prepareStatement("INSERT INTO pedido (dataPedido, produto, cliente, valorFinal, quantidade) VALUES (?, ?, ?, ?, ?)");
            stmt.setDate(1, p.getDataPedido());
            stmt.setInt(2, p.getProduto());
            stmt.setInt(3, p.getCliente());
            stmt.setDouble(4, p.getValorFinal());
            stmt.setInt(5, p.getQuantidade());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> read(int idCliente) {
        List<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT pedido.idPedido, produto.nome as nomeProduto, pedido.quantidade, produto.precoFinal * pedido.quantidade as valorFinal, pedido.dataPedido FROM pedido INNER JOIN produto ON pedido.produto = produto.idProduto WHERE pedido.cliente = ?");
            stmt.setInt(1, idCliente);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setNomeProduto(rs.getString("nomeProduto"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setValorFinal(rs.getFloat("valorFinal"));
                p.setDataPedido(rs.getDate("dataPedido"));
                pedidos.add(p);
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public List<Pedido> readTotal() {
        List<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT pedido.idPedido, usuario.nome AS nomeCliente, produto.nome AS nomeProduto, pedido.quantidade, produto.precoFinal * pedido.quantidade as valorFinal, pedido.dataPedido FROM pedido INNER JOIN usuario ON pedido.cliente = usuario.idUsuario INNER JOIN produto ON pedido.produto = produto.idProduto");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setNomeCliente(rs.getString("nomeCliente"));
                p.setNomeProduto(rs.getString("nomeProduto"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setValorFinal(rs.getFloat("valorFinal"));
                p.setDataPedido(rs.getDate("dataPedido"));
                pedidos.add(p);
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public List<Pedido> readCliente() {
        List<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM pedido WHERE cliente = ?");
            stmt.setInt(1, Usuario.getIdUsuarioAtual());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setDataPedido(rs.getDate("dataPedido"));
                p.setCliente(rs.getInt("cliente"));
                p.setProduto(rs.getInt("produto"));
                p.setQuantidade(rs.getInt("quantidade")); // Adicione esta linha
                p.setValorFinal(rs.getFloat("valorFinal"));
                pedidos.add(p);
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public void update(Pedido p) {

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conexao.prepareStatement("UPDATE pedido SET dataPedido = ?, produto = ?, cliente = ?, valorFinal = ? WHERE idPedido = ?");
            stmt.setDate(1, p.getDataPedido());
            stmt.setInt(2, p.getProduto());
            stmt.setInt(3, p.getCliente());
            stmt.setFloat(4, p.getValorFinal());
            stmt.setInt(5, p.getIdPedido());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Pedido p) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conexao.prepareStatement("DELETE FROM pedido WHERE idPedido = ?");
            stmt.setInt(1, p.getIdPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pedido buscarPedidoPorID(int idPedido) {
        String query = "SELECT * FROM pedido WHERE idPedido = ?";
        Pedido pedido = null;

        try (Connection con = Conexao.conectar();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setDataPedido(rs.getDate("dataPedido"));
                pedido.setProduto(rs.getInt("produto"));
                pedido.setQuantidade(rs.getInt("quantidade"));
                pedido.setValorFinal(rs.getFloat("valorFinal"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pedido por ID: " + e.getMessage());
        }

        return pedido;
    }

    public List<Pedido> getPedidosDoUsuario(int idUsuario) {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM pedido WHERE cliente = ?";

        try (Connection con = Conexao.conectar();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setDataPedido(rs.getDate("dataPedido"));
                pedido.setProduto(rs.getInt("produto"));
                pedido.setQuantidade(rs.getInt("quantidade"));
                pedido.setValorFinal(rs.getFloat("valorFinal"));

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar os pedidos do usuário: " + e.getMessage());
        }

        return pedidos;
    }

    public void finalizarPedido(int idUsuario) {
        try {
            Connection conn = Conexao.conectar();

            double[] valores = calcularValorTotalPedido(idUsuario);
            atualizarEstoque(conn, idUsuario);

            PreparedStatement stmtProdutos = conn.prepareStatement("SELECT produto, quantidade FROM produto_carrinho WHERE carrinho = ?");
            stmtProdutos.setInt(1, idUsuario);
            ResultSet rsProdutos = stmtProdutos.executeQuery();

            while (rsProdutos.next()) {
                int idProduto = rsProdutos.getInt("produto");
                int quantidade = rsProdutos.getInt("quantidade");

                PreparedStatement stmtPedido = conn.prepareStatement("INSERT INTO pedido (dataPedido, produto, cliente, valorFinal, quantidade) VALUES (?, ?, ?, ?, ?)");
                stmtPedido.setDate(1, new java.sql.Date(new Date().getTime()));
                stmtPedido.setInt(2, idProduto);
                stmtPedido.setInt(3, idUsuario);

                double valorFinal = valores[0];
                stmtPedido.setDouble(4, valorFinal);
                stmtPedido.setInt(5, quantidade);

                stmtPedido.executeUpdate();
                stmtPedido.close();
            }

            PreparedStatement stmtLimparCarrinho = conn.prepareStatement("DELETE FROM produto_carrinho WHERE carrinho = ?");
            stmtLimparCarrinho.setInt(1, idUsuario);
            stmtLimparCarrinho.executeUpdate();
            stmtLimparCarrinho.close();

            conn.close();
            JOptionPane.showMessageDialog(null, "Pedido feito com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Erro ao finalizar pedido: " + ex.getMessage());
        }
    }

    private void atualizarEstoque(Connection conn, int idUsuario) throws SQLException {
        PreparedStatement stmtProdutos = conn.prepareStatement("SELECT produto, quantidade FROM produto_carrinho WHERE carrinho = ?");
        stmtProdutos.setInt(1, idUsuario);
        ResultSet rsProdutos = stmtProdutos.executeQuery();

        while (rsProdutos.next()) {
            int idProduto = rsProdutos.getInt("produto");
            int quantidade = rsProdutos.getInt("quantidade");
            // Atualizar o estoque subtraindo a quantidade solicitada
            EstoqueDAO estoqueDAO = new EstoqueDAO();
            estoqueDAO.atualizarEstoque(conn, idProduto, quantidade);
        }
        stmtProdutos.close();
        rsProdutos.close();
    }

    public double[] calcularValorTotalPedido(int idUsuario) {
        double[] valores = new double[3];
        valores[0] = 0;
        valores[1] = 0;
        valores[2] = 0;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT produto.idProduto, produto.precoFinal, produto.desconto, produto_carrinho.quantidade "
                    + "FROM produto "
                    + "INNER JOIN produto_carrinho ON produto.idProduto = produto_carrinho.produto "
                    + "WHERE produto_carrinho.carrinho = ?");
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                double precoFinal = rs.getDouble("precoFinal");
                double descontoPorcentagem = rs.getDouble("desconto");
                int quantidade = rs.getInt("quantidade");

                double subtotalProduto = precoFinal * quantidade;
                double desconto = subtotalProduto * descontoPorcentagem / 100;

                valores[0] += subtotalProduto - desconto;
                valores[1] += desconto;
                valores[2] += subtotalProduto;
            }

            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valores;
    }

}
