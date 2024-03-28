package frames;

import components.Form;
import paineis.*;
import form.FormHome;
import event.EventItem;
import frames.TelaCarrinho;
import frames.TelaLoginUsuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import model.bean.Categoria;
import model.bean.ModelItem;
import model.bean.Produto;
import model.bean.Usuario;
import model.dao.CarrinhoDAO;
import model.dao.CategoriaDAO;
import model.dao.ProdutosDAO;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class TelaMenu extends javax.swing.JFrame {

    private FormHome home;
    private Animator animator;
    private Point animatePoint;
    private ModelItem itemSelected;
    private int idCate = -1;

    public TelaMenu() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
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
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> categorias = dao.read();

        for (Categoria categoria : categorias) {
            comboBoxSuggestion1.addItem(categoria.getNome());
        }
        comboBoxSuggestion1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoriaSelecionada = (String) comboBoxSuggestion1.getSelectedItem();
                int idCategoriaSelecionada = -1;
                for (Categoria categoria : categorias) {
                    if (categoria.getNome().equals(categoriaSelecionada)) {
                        idCategoriaSelecionada = categoria.getIdCategoria();
                        break;
                    }
                }
                // Faça o que quiser com o idCategoriaSelecionada
                System.out.println("ID da categoria selecionada: " + idCategoriaSelecionada);
                label_promoções.setText(categoriaSelecionada);
                idCate = idCategoriaSelecionada;
                updateProdutos();
            }
        });

        ProdutosDAO produtoDAO = new ProdutosDAO();
        List<Produto> produtos = produtoDAO.read();

        for (Produto produto : produtos) {
            boxPesquisa.addItem(produto.getNome());
        }
        boxPesquisa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String produtoSelecionada = (String) boxPesquisa.getSelectedItem();
                int idProdutoSelecionado = -1;
                for (Produto produto : produtos) {
                    if (produto.getNome().equals(produtoSelecionada)) {
                        idProdutoSelecionado = produto.getIdProduto();
                        break;
                    }
                }
                // Faça o que quiser com o idCategoriaSelecionada
                System.out.println("Produto selecionado " + idProdutoSelecionado);

            }
        });

    }

    public void atualizar() {
        updateProdutos();
    }

    private void updateProdutos() {
        home.removeAllItems();
        ProdutosDAO dao = new ProdutosDAO();
        if (idCate == -1) {
            List<ModelItem> produtos = dao.readItensPromocao();
            for (ModelItem produto : produtos) {
                home.addItem(produto);
            }
        } else {
            List<ModelItem> produtos = dao.readItens(idCate);
            for (ModelItem produto : produtos) {
                home.addItem(produto);
            }
        }

    }

    private void init() {
        home = new FormHome();
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
        ProdutosDAO dao = new ProdutosDAO();

        List<ModelItem> produtos = dao.readItens(idCate);
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
        int offsetX = 20; // ajuste conforme necessário
        int offsetY = 20; // ajuste conforme necessário

        return new Point(x + itemX - offsetX, y + itemY - offsetY);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new swing.Background();
        label_promoções = new javax.swing.JLabel();
        logo_painel = new javax.swing.JPanel();
        image_logo = new javax.swing.JLabel();
        label_mercado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        categorias = new javax.swing.JPanel();
        comboBoxSuggestion1 = new components.ComboBoxSuggestion();
        boxPesquisa = new components.ComboBoxSuggestion();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mainPanel = new swing.MainPanel();
        logo_painel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_promoções.setFont(new java.awt.Font("Sylfaen", 3, 28)); // NOI18N
        label_promoções.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_promoções.setText("Promoções");

        logo_painel.setBackground(new java.awt.Color(164, 22, 26));
        logo_painel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        image_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IAGON-Photoroom.png-Photoroom-removebg-preview-removebg-preview.png"))); // NOI18N

        label_mercado.setFont(new java.awt.Font("Sylfaen", 3, 24)); // NOI18N
        label_mercado.setForeground(new java.awt.Color(255, 255, 255));
        label_mercado.setText("Mercado");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/place.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Rua Belem 844, Londrina Paraná");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/avatar-do-usuario (1).png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/carrinho-de-compras (1).png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        categorias.setBackground(new java.awt.Color(102, 7, 8));

        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Promoções" }));

        javax.swing.GroupLayout categoriasLayout = new javax.swing.GroupLayout(categorias);
        categorias.setLayout(categoriasLayout);
        categoriasLayout.setHorizontalGroup(
            categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoriasLayout.createSequentialGroup()
                .addGap(893, 893, 893)
                .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        categoriasLayout.setVerticalGroup(
            categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoriasLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("SWItalt", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Carrinho");

        jLabel15.setFont(new java.awt.Font("SWItalt", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Minha Conta");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Produtos");

        javax.swing.GroupLayout logo_painelLayout = new javax.swing.GroupLayout(logo_painel);
        logo_painel.setLayout(logo_painelLayout);
        logo_painelLayout.setHorizontalGroup(
            logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logo_painelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_logo)
                .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(logo_painelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(label_mercado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(logo_painelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(173, 173, 173)
                                .addComponent(boxPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_painelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(490, 490, 490)))
                        .addComponent(jLabel4)
                        .addGap(144, 144, 144)
                        .addComponent(jLabel3)
                        .addGap(204, 204, 204))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_painelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel15)
                        .addGap(160, 160, 160))))
            .addComponent(categorias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        logo_painelLayout.setVerticalGroup(
            logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logo_painelLayout.createSequentialGroup()
                .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(logo_painelLayout.createSequentialGroup()
                        .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(logo_painelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_painelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label_mercado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)))
                    .addGroup(logo_painelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(image_logo)
                            .addGroup(logo_painelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addGroup(logo_painelLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(logo_painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(boxPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(categorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2068, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        logo_painel1.setBackground(new java.awt.Color(164, 22, 26));

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

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo_painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(background1Layout.createSequentialGroup()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logo_painel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(891, 891, 891)
                .addComponent(label_promoções, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addComponent(logo_painel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_promoções)
                .addGap(60, 60, 60)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(logo_painel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );

        getContentPane().add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1940, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (Usuario.getIdUsuarioAtual() != 0) {
            TelaPerfilUsuarios u = new TelaPerfilUsuarios();
            u.setVisible(true);
        } else {
            TelaLoginUsuario login = new TelaLoginUsuario();
            login.setVisible(true);
        }

    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        TelaCarrinho carrinho = new TelaCarrinho();
        carrinho.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

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
            java.util.logging.Logger.getLogger(TelaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Background background1;
    private components.ComboBoxSuggestion boxPesquisa;
    private javax.swing.JPanel categorias;
    private components.ComboBoxSuggestion comboBoxSuggestion1;
    private javax.swing.JLabel image_logo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel label_mercado;
    private javax.swing.JLabel label_promoções;
    private javax.swing.JPanel logo_painel;
    private javax.swing.JPanel logo_painel1;
    private swing.MainPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
