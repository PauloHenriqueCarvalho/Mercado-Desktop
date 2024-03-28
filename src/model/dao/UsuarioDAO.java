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
import model.bean.Usuario;

/**
 *
 * @author Senai
 */
public class UsuarioDAO {

    public List<Usuario> readAll() {
        List<Usuario> usuarios = new ArrayList();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM usauario");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getString("telefone"));
                u.setAcesso(rs.getString("acesso"));
                usuarios.add(u);
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
    
     public List<Usuario> readAll2() {
        List<Usuario> usuarios = new ArrayList();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM usauario");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getString("telefone"));
                u.setAcesso(rs.getString("acesso"));
                usuarios.add(u);
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public Usuario readUsuario(int idUsuario) {

        Usuario u = new Usuario();

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM usuario WHERE idUsuario = ?");
            stmt.setInt(1, idUsuario);

            rs = stmt.executeQuery();

            if (rs.next()) {
                u.setIdUsuario(idUsuario);
                u.setLogin(rs.getString("login"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getString("telefone"));
                u.setAcesso(rs.getString("acesso"));
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public int login(String login, String senha) {
        int valida = 0;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conexao.prepareStatement("SELECT * FROM usuario WHERE login = ? AND senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            

            rs = stmt.executeQuery();
            int acesso = 0;
            if (rs.next()) {
                Usuario.setIdUsuarioAtual(rs.getInt("idUsuario"));
                Usuario.setLoginAtual(rs.getString("login"));
                Usuario.setNomeAtual(rs.getString("nome"));
                String tal = rs.getString("acesso");
                if(tal.equals("cliente")){
                    valida = 1;
                } else {
                    valida = 2;
                }
                
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valida;
    }

    public boolean create(Usuario u) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conexao.prepareStatement("INSERT INTO usuario (login, nome, senha, telefone) VALUES (?,?,?,?)");
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getNome());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getTelefone());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public void update(Usuario u) {
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt;
            
            stmt = conexao.prepareStatement("UPDATE usuario SET login = ?, nome = ?, senha = ?, telefone = ?, acesso = ? WHERE idUsuario = ?");
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getNome());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getTelefone());
            stmt.setString(5, u.getAcesso());
            stmt.setInt(6, u.getIdUsuario());
            
            stmt.executeUpdate();
            
            stmt.close();
            conexao.close();            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(Usuario u) {
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt;
            
            stmt = conexao.prepareStatement("DELETE FROM usuario WHERE idUsuario = ?");
            stmt.setInt(1, u.getIdUsuario());
            
            stmt.executeUpdate();
            
            stmt.close();
            conexao.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
