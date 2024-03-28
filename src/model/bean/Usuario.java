/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author Senai
 */
public class Usuario {

    private static int idUsuarioAtual;
    private static String loginAtual;
    private static String nomeAtual;
    private int idUsuario;
    private String login;
    private String nome;
    private String senha;
    private String telefone;
    private String acesso;

    public Usuario(int idUsuario, String login, String nome, String senha, String telefone, String acesso) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.acesso = acesso;
    }

    public Usuario() {
    }

    public static int getIdUsuarioAtual() {
        return idUsuarioAtual;
    }

    public static void setIdUsuarioAtual(int idUsuarioAtual) {
        Usuario.idUsuarioAtual = idUsuarioAtual;
    }

    public static String getLoginAtual() {
        return loginAtual;
    }

    public static void setLoginAtual(String loginAtual) {
        Usuario.loginAtual = loginAtual;
    }

    public static String getNomeAtual() {
        return nomeAtual;
    }

    public static void setNomeAtual(String nomeAtual) {
        Usuario.nomeAtual = nomeAtual;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }
    
    
}
