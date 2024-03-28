/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Senai
 */
public class Pedido {

    private int idPedido;
    private String dataPedidoString;
    private Date dataPedido;
    private int produto;
    private int cliente;
    private Float valorFinal;
    private int quantidade;
    private String nomeProduto;
    private String nomeCliente;

    public static List<Pedido> getPedidosDoUsuario(int idUsuario) {
        List<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conexao = Conexao.conectar();

            String query = "SELECT p.idPedido, DATE_FORMAT(p.dataPedido, '%d/%m/%Y') AS dataPedidoFormatada, "
                    + "pr.nome AS nomeProduto, c.nome AS nomeCliente, "
                    + "p.quantidade, p.valorFinal "
                    + "FROM pedido p "
                    + "INNER JOIN produto pr ON p.produto = pr.idProduto "
                    + "INNER JOIN cliente c ON p.cliente = c.idCliente "
                    + "WHERE p.cliente = ?";

            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultSet.getInt("idPedido"));
                pedido.setDataPedidoString(resultSet.getString("dataPedidoFormatada"));
                pedido.setNomeProduto(resultSet.getString("nomeProduto"));
                pedido.setNomeCliente(resultSet.getString("nomeCliente"));
                pedido.setQuantidade(resultSet.getInt("quantidade"));
                pedido.setValorFinal(resultSet.getFloat("valorFinal"));
                pedidos.add(pedido);
            }

            resultSet.close();
            statement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public Pedido(int idPedido, String dataPedidoString, Date dataPedido, int produto, int cliente, Float valorFinal, int quantidade, String nomeProduto, String nomeCliente) {
        this.idPedido = idPedido;
        this.dataPedidoString = dataPedidoString;
        this.dataPedido = dataPedido;
        this.produto = produto;
        this.cliente = cliente;
        this.valorFinal = valorFinal;
        this.quantidade = quantidade;
        this.nomeProduto = nomeProduto;
        this.nomeCliente = nomeCliente;
    }

    public Pedido() {
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDataPedidoString() {
        return dataPedidoString;
    }

    public void setDataPedidoString(String dataPedidoString) {
        this.dataPedidoString = dataPedidoString;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public Float getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Float valorFinal) {
        this.valorFinal = valorFinal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

}
