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
public class ProdutoCarrinho {
    private int idProduto_carrinho;
    private int carrinho;
    private int produto;
    private int quantidade;

    public ProdutoCarrinho() {
    }

    public ProdutoCarrinho(int idProduto_carrinho, int carrinho, int produto, int quantidade) {
        this.idProduto_carrinho = idProduto_carrinho;
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getIdProduto_carrinho() {
        return idProduto_carrinho;
    }

    public void setIdProduto_carrinho(int idProduto_carrinho) {
        this.idProduto_carrinho = idProduto_carrinho;
    }

    public int getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(int carrinho) {
        this.carrinho = carrinho;
    }

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    

    
    
    
}
