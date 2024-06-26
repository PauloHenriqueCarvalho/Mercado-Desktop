/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframes;

import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import model.bean.Empresa;
import model.bean.Model_Card;
import model.dao.EmpresaDAO;

/**
 *
 * @author paulo
 */
public class InternalInicio extends javax.swing.JInternalFrame {

    /**
     * Creates new form InternalInicio
     */
    public InternalInicio() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        EmpresaDAO dao = new EmpresaDAO();
        dao.inserirValoresEmpresa();

        ui.setNorthPane(null);
        Empresa e = new Empresa();
        e = dao.obterValoresEmpresa();
        
        DecimalFormat df = new DecimalFormat("0.00");
        double valorVendido = dao.calcularValorVendido();
        String valorFormatado = df.format(valorVendido);
        
        double valorLucro = dao.calcularValorLucro();
        String lucroFormatado = df.format(valorLucro);
        
        double valorEstoque = dao.calcularValorEstoque();
        String estoqueFormatado = df.format(valorEstoque);

        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/images/stock.png")), "Total Vendido", "$" + valorFormatado, "Total vendido pelo mercado"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/images/profit.png")), "Total de Lucro", "$" + lucroFormatado, "Total de lucro do mercado"));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/images/flag.png")), "Total em Estoque", "$" + estoqueFormatado, "Total do valor de produtos em estoque"));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        card1 = new components.Card();
        card2 = new components.Card();
        card3 = new components.Card();

        setPreferredSize(new java.awt.Dimension(1920, 1080));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        card1.setColor1(new java.awt.Color(142, 142, 250));
        card1.setColor2(new java.awt.Color(123, 123, 245));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 273;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(45, 313, 828, 0);
        getContentPane().add(card1, gridBagConstraints);

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 273;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(45, 70, 828, 0);
        getContentPane().add(card2, gridBagConstraints);

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 273;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(45, 70, 828, 314);
        getContentPane().add(card3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.Card card1;
    private components.Card card2;
    private components.Card card3;
    // End of variables declaration//GEN-END:variables
}
