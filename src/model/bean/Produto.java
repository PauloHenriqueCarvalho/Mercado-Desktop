/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import java.sql.Blob;

/**
 *
 * @author Senai
 */
public class Produto {
   private int idProduto;
   private String nome;
   private int categoria;
   private int quantidade;
   private Float preco;
   private int desconto;
   private Float descontoPorcentagem;
   private Float precoFinal;
   private Blob icon;

    public Produto() {
    }

    public Produto(int idProduto, String nome, int categoria, int quantidade, Float preco, int desconto, Float descontoPorcentagem, Float precoFinal, Blob icon) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
        this.desconto = desconto;
        this.descontoPorcentagem = descontoPorcentagem;
        this.precoFinal = precoFinal;
        this.icon = icon;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public Float getDescontoPorcentagem() {
        return descontoPorcentagem;
    }

    public void setDescontoPorcentagem(Float descontoPorcentagem) {
        this.descontoPorcentagem = descontoPorcentagem;
    }

    public Float getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(Float precoFinal) {
        this.precoFinal = precoFinal;
    }

    public Blob getIcon() {
        return icon;
    }

    public void setIcon(Blob icon) {
        this.icon = icon;
    }

    

    
   
   
   
    
    
}
