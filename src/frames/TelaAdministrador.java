/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import com.formdev.flatlaf.FlatDarkLaf;
import components.HeaderAdm;
import internalframes.InternalCategorias;
import internalframes.InternalEstoque;
import internalframes.InternalInicio;
import internalframes.InternalPedidos;
import internalframes.InternalProduto;
import java.awt.Color;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import javaswingdev.drawer.DrawerItem;
import javaswingdev.drawer.EventDrawer;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author Senai
 */
public class TelaAdministrador extends javax.swing.JFrame {

    private DrawerController drawer;
    public TelaAdministrador() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initComponents();
        
        desk.removeAll();
        InternalInicio inicio = new InternalInicio();
        desk.add(inicio).setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
        drawer=Drawer.newDrawer(this)
                .header(new HeaderAdm())
                .space(10)
                .backgroundTransparent(0.3f)               
                .duration(300)
                .drawerBackground(new Color(111, 111, 111))
                .addChild(new DrawerItem("Inicio").icon(new ImageIcon(getClass().getResource("/images/data.png"))).build())
                .addChild(new DrawerItem("Produtos").icon(new ImageIcon(getClass().getResource("/images/4.png"))).build())
                .addChild(new DrawerItem("Estoque").icon(new ImageIcon(getClass().getResource("/images/promocao.png"))).build())
                .addChild(new DrawerItem("Categorias").icon(new ImageIcon(getClass().getResource("/images/config.png"))).build())
                .addChild(new DrawerItem("Pedidos").icon(new ImageIcon(getClass().getResource("/images/5.png"))).build())
                
                .addFooter(new DrawerItem("Sair").icon(new ImageIcon(getClass().getResource("/images/exit.png"))).build())
                .event(new EventDrawer() {
                    @Override
                    public void selected(int i, DrawerItem di){
                        if(i == 0){
                            desk.removeAll();
                            desk.add(inicio).setVisible(true);
                            drawer.hide();
                        }
                        else if(i == 1){
                            InternalProduto produto = new InternalProduto();
                            desk.removeAll();
                            desk.add(produto).setVisible(true);
                            drawer.hide();
                        } else if (i == 2){
                            InternalEstoque est = new InternalEstoque();
                            desk.removeAll();
                            desk.add(est).setVisible(true);
                            drawer.hide();
                            
                        } else if (i == 3){
                            InternalCategorias conf = new InternalCategorias();
                            desk.removeAll();
                            desk.add(conf).setVisible(true);
                            drawer.hide();
                        }  else if (i == 4){
                            InternalPedidos conf = new InternalPedidos();
                            desk.removeAll();
                            desk.add(conf).setVisible(true);
                            drawer.hide();
                        } 
                        
                        
                    }
                })
                .build();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBtn = new javax.swing.JButton();
        desk = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn.setText("Menu");
        menuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBtnActionPerformed(evt);
            }
        });
        getContentPane().add(menuBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        getContentPane().add(desk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1930, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBtnActionPerformed
        if(drawer.isShow()){
            drawer.hide();
        } else {
            drawer.show();
        }
    }//GEN-LAST:event_menuBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desk;
    private javax.swing.JButton menuBtn;
    // End of variables declaration//GEN-END:variables
}
