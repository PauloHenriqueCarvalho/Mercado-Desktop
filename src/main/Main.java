/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import frames.TelaAdministrador;
import frames.TelaLoginUsuario;
import model.dao.UsuarioDAO;

/**
 *
 * @author Senai
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TelaLoginUsuario a = new TelaLoginUsuario();
        a.setVisible(true);
    }
    
}
