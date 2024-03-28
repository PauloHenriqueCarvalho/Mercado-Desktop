/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author paulo
 */
public class Empresa {
   private static int id;
   private float valorEstoque;
   private float valorVendido;
   private float valorLucro;

    public Empresa() {
    }

    public Empresa(float valorEstoque, float valorVendido, float valorLucro) {
        this.valorEstoque = valorEstoque;
        this.valorVendido = valorVendido;
        this.valorLucro = valorLucro;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Empresa.id = id;
    }

    public float getValorEstoque() {
        return valorEstoque;
    }

    public void setValorEstoque(float valorEstoque) {
        this.valorEstoque = valorEstoque;
    }

    public float getValorVendido() {
        return valorVendido;
    }

    public void setValorVendido(float valorVendido) {
        this.valorVendido = valorVendido;
    }

    public float getValorLucro() {
        return valorLucro;
    }

    public void setValorLucro(float valorLucro) {
        this.valorLucro = valorLucro;
    }
   
   
   
}
