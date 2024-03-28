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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Estoque;
import model.bean.Produto;

/**
 *
 * @author Senai
 */
public class EstoqueDAO {
    
    public void atualizarEstoque(Connection conn, int idProduto, int quantidadeSolicitada) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE estoque SET quantidade = quantidade - ? WHERE produto = ?"
        );
        stmt.setInt(1, quantidadeSolicitada);
        stmt.setInt(2, idProduto);
        stmt.executeUpdate();
        stmt.close();
    }   

    public void createEstoque(int idProduto, int quantidade) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO estoque (produto, quantidade) VALUES (?,?)");
            ps.setInt(1, idProduto);
            ps.setInt(2, quantidade);
            ps.executeUpdate();
            System.out.println("Estoque criado para o produto: " + idProduto);
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Estoque readEstoqueProduto(int idProduto) {
        Estoque estoque = null;
        String query = "SELECT * FROM estoque WHERE produto = ?";

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement(query);

            ps.setInt(1, idProduto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    estoque = new Estoque();
                    estoque.setIdEstoque(rs.getInt("idEstoque"));
                    estoque.setProduto(rs.getInt("produto"));
                    estoque.setQuantidade(rs.getInt("quantidade"));
                    estoque.setDataAtualizacao(rs.getTimestamp("dataAtualizacaoEstoque"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estoque;
    }

    public List<Estoque> readEstoque() {
        List<Estoque> estoqueGeral = new ArrayList<>();
        String query = "SELECT * FROM estoque";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement ps = conexao.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estoque estoque = new Estoque();
                estoque.setIdEstoque(rs.getInt("idEstoque"));
                estoque.setProduto(rs.getInt("produto"));
                estoque.setQuantidade(rs.getInt("quantidade"));
                estoque.setDataAtualizacao(rs.getTimestamp("dataAtualizacaoEstoque"));

                estoqueGeral.add(estoque);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estoqueGeral;
    }

    public void update(Estoque estoque) {
        String query = "UPDATE estoque SET quantidade = ?, custo = ? WHERE idEstoque = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement ps = conexao.prepareStatement(query)) {

            ps.setInt(1, estoque.getQuantidade());
            ps.setInt(3, estoque.getIdEstoque());

            ps.executeUpdate();
            System.out.println("Estoque atualizado com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int idEstoque) {
        String query = "DELETE FROM estoque WHERE idEstoque = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement ps = conexao.prepareStatement(query)) {

            ps.setInt(1, idEstoque);

            ps.executeUpdate();
            System.out.println("Estoque excluído com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Estoque estoque) {
        String query = "INSERT INTO estoque (produto, quantidade, custo) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement ps = conexao.prepareStatement(query)) {

            ps.setInt(1, estoque.getProduto());
            ps.setInt(2, estoque.getQuantidade());

            ps.executeUpdate();
            System.out.println("Estoque inserido com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int consultarQuantidadeDisponivel(int idProduto) {
    int quantidadeDisponivel = 0;
    try {
        Connection conexao = Conexao.conectar();
        PreparedStatement stmt = conexao.prepareStatement("SELECT quantidade FROM estoque WHERE produto = ?");
        stmt.setInt(1, idProduto);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            quantidadeDisponivel = rs.getInt("quantidade");
        }
        rs.close();
        stmt.close();
        conexao.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return quantidadeDisponivel;
}
    
    public void diminuirQuantidade(int idProduto, int quantidade) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("UPDATE estoque SET quantidade = quantidade - 1 WHERE produto = ?");
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aumentarQuantidade(int idProduto, int quantidade) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("UPDATE estoque SET quantidade = quantidade + 1 WHERE produto = ?");
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Estoque obterEstoque(int idProduto) {
        Estoque estoque = null;
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM estoque WHERE produto = ?");
            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idEstoque = rs.getInt("idEstoque");
                int quantidade = rs.getInt("quantidade");
                Timestamp dataAtualizacao = rs.getTimestamp("dataAtualizacaoEstoque");
                estoque = new Estoque(idEstoque, idProduto, quantidade, dataAtualizacao);
            }
            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estoque;
    }
}
