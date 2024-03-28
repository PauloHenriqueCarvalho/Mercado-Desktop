/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import conexao.Conexao;
import event.EventItem;
import form.FormCarrinho;
import form.FormEstoque;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Categoria;
import model.bean.ModelItem;
import model.bean.Produto;
import model.bean.Usuario;
import model.dao.CarrinhoDAO;
import model.dao.CategoriaDAO;
import model.dao.EmpresaDAO;
import model.dao.PedidosDAO;
import model.dao.ProdutosDAO;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 *
 * @author Senai
 */
public class TelaCarrinho extends javax.swing.JFrame {

    /**
     * Creates new form TelaCarrinho
     */
    private FileInputStream fis;
    private int tamanho;
    int idProduto = -1;
    private FormCarrinho home;
    private Animator animator;
    private Point animatePoint;
    private ModelItem itemSelected;
    private int idCate = 1;

    public TelaCarrinho() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        Conexao conexao = new Conexao();
        Produto produto = new Produto();
        PedidosDAO pedidoDAO = new PedidosDAO();
        double[] valores = pedidoDAO.calcularValorTotalPedido(Usuario.getIdUsuarioAtual());

        DecimalFormat df = new DecimalFormat("0.00");
        String[] valoresFormatados = new String[valores.length];
        for (int i = 0; i < valores.length; i++) {
            valoresFormatados[i] = df.format(valores[i]);
        }

        lTotal.setText(valoresFormatados[2]);
        init();
        updateProdutos();

        animator = PropertySetter.createAnimator(500, mainPanel, "imageLocation", animatePoint, mainPanel.getTargetLocation());
        animator.addTarget(new PropertySetter(mainPanel, "imageSize", new Dimension(180, 120), mainPanel.getTargetSize()));
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void end() {
                mainPanel.setImageOld(null);
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);

    }

    private void updateProdutos() {
        home.removeAllItems();
        CarrinhoDAO dao = new CarrinhoDAO();

        List<ModelItem> produtos = dao.readCarrinho(Usuario.getIdUsuarioAtual());
        for (ModelItem produto : produtos) {
            home.addItem(produto);
        }

    }

    private void init() {
        home = new FormCarrinho();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);
        testData();
    }

    private void testData() {
        home.setEvent(new EventItem() {
            @Override
            public void itemClick(Component com, ModelItem item) {
                if (itemSelected != null) {
                    mainPanel.setImageOld(itemSelected.getImage());
                }
                if (itemSelected != item) {
                    if (!animator.isRunning()) {
                        itemSelected = item;
                        animatePoint = getLocationOf(com);
                        animatePoint.y += 30;
                        mainPanel.setImage(item.getImage());
                        mainPanel.setImageLocation(animatePoint);
                        mainPanel.setImageSize(new Dimension(180, 120));
                        mainPanel.repaint();
                        home.setSelected(com);
                        home.showItem(item);
                        animator.start();
                    }
                }
            }
        });
        CarrinhoDAO dao = new CarrinhoDAO();

        List<ModelItem> produtos = dao.readCarrinho(Usuario.getIdUsuarioAtual());
        for (ModelItem produto : produtos) {
            home.addItem(produto);
        }
    }

    private Point getLocationOf(Component com) {
        Point p = home.getPanelItemLocation();
        int x = p.x;
        int y = p.y;
        int itemX = com.getX();
        int itemY = com.getY();
        int offsetX = 20;
        int offsetY = 20;
        return new Point(x + itemX - offsetX, y + itemY - offsetY);
    }

    public void finalizarPedidoSeCarrinhoNaoVazio(int idUsuario) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        if (carrinhoDAO.verificarCarrinhoVazio(idUsuario)) {
            JOptionPane.showMessageDialog(null, "O carrinho está vazio. Adicione produtos ao carrinho antes de finalizar o pedido");
        } else {
            PedidosDAO pedidosDAO = new PedidosDAO();
            pedidosDAO.finalizarPedido(idUsuario);
            DecimalFormat df = new DecimalFormat("0.00");
            EmpresaDAO dao = new EmpresaDAO();
            double valorVendido = dao.calcularValorVendido();
            String valorFormatado = df.format(valorVendido);

            double valorLucro = dao.calcularValorLucro();
            String lucroFormatado = df.format(valorLucro);

            double valorEstoque = dao.calcularValorEstoque();
            String estoqueFormatado = df.format(valorEstoque);
            TelaMenu m = new TelaMenu();
            this.dispose();
            m.setVisible(true);
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

        painel_global = new javax.swing.JPanel();
        logo_painel = new javax.swing.JPanel();
        image_logo = new javax.swing.JLabel();
        label_mercado = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Total = new javax.swing.JLabel();
        logo_painel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        background1 = new swing.Background();
        mainPanel = new swing.MainPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        painel_global.setBackground(new java.awt.Color(255, 255, 255));
        painel_global.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo_painel.setBackground(new java.awt.Color(0, 0, 0));

        image_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IAGON-Photoroom.png-Photoroom-removebg-preview-removebg-preview.png"))); // NOI18N
        image_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                image_logoMouseClicked(evt);
            }
        });

        label_mercado.setFont(new java.awt.Font("Sylfaen", 3, 24)); // NOI18N
        label_mercado.setForeground(new java.awt.Color(255, 255, 255));
        label_mercado.setText("Mercado");

        jLabel10.setFont(new java.awt.Font("Harlow Solid Italic", 1, 44)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Carrinho");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/carrinho-de-compras.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Minha conta");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/avatar-do-usuario (1).png"))); // NOI18N

        javax.swing.GroupLayout logo_painelLayout = new javax.swing.GroupLayout(logo_painel);
        logo_painel.setLayout(logo_painelLayout);
        logo_painelLayout.setHorizontalGroup(
            logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logo_painelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_mercado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1309, Short.MAX_VALUE)
                .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_painelLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_painelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(35, 35, 35))))
        );
        logo_painelLayout.setVerticalGroup(
            logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logo_painelLayout.createSequentialGroup()
                .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(logo_painelLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13))
                    .addComponent(jLabel12)
                    .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logo_painelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(image_logo))
                        .addGroup(logo_painelLayout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(label_mercado)))
                    .addComponent(jLabel10))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        painel_global.add(logo_painel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 120));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Finalizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        painel_global.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 250, 50));

        Total.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Total.setText("Total:");
        painel_global.add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, -1, -1));

        logo_painel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("© Mercado Iagon. Todos os direitos Reservados");

        javax.swing.GroupLayout logo_painel1Layout = new javax.swing.GroupLayout(logo_painel1);
        logo_painel1.setLayout(logo_painel1Layout);
        logo_painel1Layout.setHorizontalGroup(
            logo_painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_painel1Layout.createSequentialGroup()
                .addContainerGap(755, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(736, 736, 736))
        );
        logo_painel1Layout.setVerticalGroup(
            logo_painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logo_painel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        painel_global.add(logo_painel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 960, 1920, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Atualizar Carrinho");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1640, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(403, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Resumo do Pedido");

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(16, 16, 16))
        );

        painel_global.add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1920, 530));

        lTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        painel_global.add(lTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 690, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1920, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(painel_global, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(painel_global, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        PedidosDAO p = new PedidosDAO();
        finalizarPedidoSeCarrinhoNaoVazio(Usuario.getIdUsuarioAtual());
        updateProdutos();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void image_logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_image_logoMouseClicked
        TelaMenu principal = new TelaMenu();
        principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_image_logoMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PedidosDAO pedidoDAO = new PedidosDAO();
        double[] valores = pedidoDAO.calcularValorTotalPedido(Usuario.getIdUsuarioAtual());


        lTotal.setText(String.valueOf(valores[2]));

        updateProdutos();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCarrinho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCarrinho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCarrinho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCarrinho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCarrinho().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Total;
    private swing.Background background1;
    private javax.swing.JLabel image_logo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel lTotal;
    private javax.swing.JLabel label_mercado;
    private javax.swing.JPanel logo_painel;
    private javax.swing.JPanel logo_painel1;
    private swing.MainPanel mainPanel;
    private javax.swing.JPanel painel_global;
    // End of variables declaration//GEN-END:variables
}
