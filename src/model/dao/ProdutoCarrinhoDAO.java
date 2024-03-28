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
import javax.swing.JOptionPane;

/**
 *
 * @author Senai
 */
public class ProdutoCarrinhoDAO {

    public void adicionarProdutoAoCarrinho(int idProduto, int idUsuario, int quantidade) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO produto_carrinho (carrinho, produto, quantidade) VALUES (?, ?, ?)");
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProduto);
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();
            stmt.close();
            conn.close();

            System.out.println("Produto adicionado ao carrinho com sucesso.");
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar produto ao carrinho: " + ex.getMessage());
        }
    }

    public boolean verificarItemNoCarrinho(int idUsuario, int idProduto) {

        try {
            Connection conn = Conexao.conectar();
            String query = "SELECT * FROM produto_carrinho WHERE carrinho = ? AND produto = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, idUsuario);
            statement.setInt(2, idProduto);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void removerProdutoDoCarrinho(int idProduto, int idUsuario) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM produto_carrinho WHERE carrinho = ? AND produto = ?");
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProduto);
            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            conn.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto removido do carrinho com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado no carrinho");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao remover produto do carrinho: " + ex.getMessage());
        }
    }
}
