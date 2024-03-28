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
import java.util.List;
import model.bean.Categoria;

/**
 *
 * @author Senai
 */
public class CategoriaDAO {

    public void create(Categoria categoria) {

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            stmt = conexao.prepareStatement("INSERT INTO categoria (nome) VALUES (?)");
            stmt.setString(1, categoria.getNome());

            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir categoria: " + e);
        }
    }

    public List<Categoria> read() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM categoria");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));

                categorias.add(categoria);
            }
            rs.close();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao ler categorias: " + e);
        }

        return categorias;
    }

    public void update(Categoria categoria) {

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            stmt = conexao.prepareStatement("UPDATE categoria SET nome = ? WHERE idCategoria = ?");
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getIdCategoria());

            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar categoria: " + e);
        }
    }

    public void delete(Categoria categoria) {

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            PreparedStatement stmtDeleteProdutos = null;
            stmtDeleteProdutos = conexao.prepareStatement("DELETE FROM Produto WHERE categoria = ?");
            stmtDeleteProdutos.setInt(1, categoria.getIdCategoria());
            stmtDeleteProdutos.executeUpdate();
            stmtDeleteProdutos.close();
            
            stmt = conexao.prepareStatement("DELETE FROM categoria WHERE idCategoria = ?");
            stmt.setInt(1, categoria.getIdCategoria());
            stmt.executeUpdate();
            stmt.close();
            
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir categoria: " + e);
        }
    }
    
    public boolean categoriaJaExiste(String nomeCategoria) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("SELECT COUNT(*) AS total FROM categoria WHERE nome = ?");
            stmt.setString(1, nomeCategoria);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int total = rs.getInt("total");
            rs.close();
            stmt.close();
            conexao.close();

            // Se o total for maior que 0, significa que a categoria já existe
            return total > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar categoria existente: " + e);
            return false;
        }
    } 
}

