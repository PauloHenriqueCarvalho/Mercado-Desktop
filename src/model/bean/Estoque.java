/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import java.sql.Timestamp;

/**
 *
 * @author Senai
 */
public class Estoque {
    private int idEstoque;
    private int produto;
    private int quantidade;
    private Timestamp dataAtualizacao;

    public Estoque() {
    }

    public Estoque(int idEstoque, int produto, int quantidade, Timestamp dataAtualizacao) {
        this.idEstoque = idEstoque;
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataAtualizacao = dataAtualizacao;
    }
    
    
    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
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



    public Timestamp getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Timestamp dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}