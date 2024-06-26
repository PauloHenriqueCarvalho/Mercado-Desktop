/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import com.formdev.flatlaf.FlatDarkLaf;
import conexao.Conexao;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.bean.Produto;
import model.dao.ProdutosDAO;

/**
 *
 * @author Senai
 */
public class MostrarProduto extends javax.swing.JFrame {

    Conexao conexao = new Conexao();
    Produto produto = new Produto();
    private FileInputStream fis;
    private int tamanho;

    /**
     * Creates new form EditarProduto
     */
    public MostrarProduto() {
        initComponents();
    }



    private void buscarRA(int id) {
       String readRA = "select * from produto where idProduto = ?";
       try{
           Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement(readRA);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                nome.setText(rs.getString("nome"));
                porc.setText("" + rs.getInt("desconto"));
                qtd.setText("" + rs.getInt("quantidade"));
                est.setText("" + rs.getFloat("preco"));
                vend.setText("" + rs.getFloat("precoFinal"));
                cat.setText("" + rs.getInt("categoria"));
                Blob blob = (Blob) rs.getBlob("imagem");
                byte[] img = blob.getBytes(1, (int) blob.length());
                BufferedImage imagem = null;
                try{
                    imagem = ImageIO.read(new ByteArrayInputStream(img));
                } catch (Exception e ) {
                    System.out.println(e);
                }
                ImageIcon icone = new ImageIcon(imagem);
                Icon foto = new ImageIcon(icone.getImage().getScaledInstance(txtImagem.getWidth(), txtImagem.getHeight(), Image.SCALE_SMOOTH));
                txtImagem.setIcon(foto);
            }
            
       } catch(SQLException e) {
           System.out.println(e);
       }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        nome = new javax.swing.JLabel();
        porc = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCad = new javax.swing.JButton();
        qtd = new javax.swing.JLabel();
        est = new javax.swing.JLabel();
        vend = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtImagem = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        cat = new javax.swing.JLabel();
        sId = new javax.swing.JSpinner();
        sIda = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nome.setText("a");
        getContentPane().add(nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        porc.setText("a");
        getContentPane().add(porc, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        btnCad.setText("Buscar Produto");
        btnCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadActionPerformed(evt);
            }
        });
        getContentPane().add(btnCad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, -1, -1));

        qtd.setText("a");
        getContentPane().add(qtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 10, 10));

        est.setText("a");
        getContentPane().add(est, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, -1, -1));

        vend.setText("a");
        getContentPane().add(vend, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        txtImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stock.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 290, 320));

        jButton2.setText("Carregar Foto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, -1, -1));

        cat.setText("a");
        getContentPane().add(cat, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, -1, -1));
        getContentPane().add(sId, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 60, -1));

        sIda.setText("ID:");
        getContentPane().add(sIda, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadActionPerformed
        buscarRA((int) sId.getValue());
    }//GEN-LAST:event_btnCadActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MostrarProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCad;
    private javax.swing.JLabel cat;
    private javax.swing.JLabel est;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nome;
    private javax.swing.JLabel porc;
    private javax.swing.JLabel qtd;
    private javax.swing.JSpinner sId;
    private javax.swing.JLabel sIda;
    private javax.swing.JLabel txtImagem;
    private javax.swing.JLabel vend;
    // End of variables declaration//GEN-END:variables
}
