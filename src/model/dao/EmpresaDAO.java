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
import model.bean.Empresa;

/**
 *
 * @author paulo
 */
public class EmpresaDAO {

    public void inserirValoresEmpresa() {
        double valorEstoque = calcularValorEstoque();

        double valorVendido = calcularValorVendido();

        double valorLucro = valorVendido - valorEstoque;

        String insertQuery = "INSERT INTO empresa (valorEstoque, valorVendido, valorLucro) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
                PreparedStatement insertStatement = conexao.prepareStatement(insertQuery)) {

            insertStatement.setDouble(1, valorEstoque);
            insertStatement.setDouble(2, valorVendido);
            insertStatement.setDouble(3, valorLucro);
            insertStatement.executeUpdate();

            System.out.println("Valores inseridos na tabela empresa com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calcularValorEstoque() {
        String query = "SELECT produto.preco, estoque.quantidade FROM produto INNER JOIN estoque ON produto.idProduto = estoque.Produto";

        double valorEstoque = 0;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                double valorProduto = preco * quantidade;
                valorEstoque += valorProduto;
            }

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valorEstoque;
    }

    public double calcularValorVendido() {
        String query = "Select * from pedido";
        double vendido = 0;
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vendido += rs.getFloat("valorFinal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendido;
    }

    public double calcularValorVendidoEstoque() {
        String query = "SELECT pedido.Produto, pedido.quantidade, produto.preco FROM pedido INNER JOIN produto ON pedido.Produto = produto.idProduto";

        double vendidoEstoque = 0;
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement ps = conexao.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idProduto = rs.getInt("Produto");
                int quantidade = rs.getInt("quantidade");
                double precoProduto = rs.getDouble("preco");
                double valorVendidoProduto = precoProduto * quantidade;
                vendidoEstoque += valorVendidoProduto;
            }
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendidoEstoque;
    }

    public double calcularValorLucro() {
        double valorLucro;
        valorLucro = calcularValorVendido() - calcularValorVendidoEstoque();
        return valorLucro;
    }

    public Empresa obterValoresEmpresa() {
        String query = "SELECT valorEstoque, valorVendido, valorLucro FROM empresa";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement ps = conexao.prepareStatement(query);
                ResultSet resultSet = ps.executeQuery()) {

            if (resultSet.next()) {
                double valorEstoque = resultSet.getDouble("valorEstoque");
                double valorVendido = resultSet.getDouble("valorVendido");
                double valorLucro = resultSet.getDouble("valorLucro");

                Empresa empresa = new Empresa();
                empresa.setValorEstoque((float) valorEstoque);
                empresa.setValorVendido((float) valorVendido);
                empresa.setValorLucro((float) valorLucro);

                return empresa;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
