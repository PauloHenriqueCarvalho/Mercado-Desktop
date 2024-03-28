/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframes;

import conexao.Conexao;
import event.EventItem;
import form.FormHome;
import form.FormProdutos;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import model.bean.Categoria;
import model.bean.Estoque;
import model.bean.ModelItem;
import model.bean.Produto;
import model.dao.CategoriaDAO;
import model.dao.EstoqueDAO;
import model.dao.ProdutosDAO;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 *
 * @author paulo
 */
public class InternalProduto extends javax.swing.JInternalFrame {

    private FileInputStream fis;
    private int tamanho;
    int idCategoria = -1;
    int idProduto = -1;
    private FormProdutos home;
    private Animator animator;
    private Point animatePoint;
    private ModelItem itemSelected;
    private int idCate = -1;

    /**
     * Creates new form InternalInicio
     */
    public InternalProduto() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        Conexao conexao = new Conexao();
        Produto produto = new Produto();
        init();
        updateProdutos();
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> categorias = dao.read();

        for (Categoria categoria : categorias) {
            inputCategoria.addItem(categoria.getNome());
        }
        inputCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoriaSelecionada = (String) inputCategoria.getSelectedItem();
                int idCategoriaSelecionada = -1;
                for (Categoria categoria : categorias) {
                    if (categoria.getNome().equals(categoriaSelecionada)) {
                        idCategoriaSelecionada = categoria.getIdCategoria();
                        break;
                    }
                }
                // Faça o que quiser com o idCategoriaSelecionada
                System.out.println("ID da categoria selecionada: " + idCategoriaSelecionada);
                idCategoria = idCategoriaSelecionada;

            }
        });

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

                idCate = idCategoriaSelecionada;
                updateProdutos();
            }
        });

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
        home = new FormProdutos();
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

    private void validarCamposPrecos() {
        try {
            int precoEstoque = Integer.parseInt(inputPrecoEstoque.getText());
            int precoVendido = Integer.parseInt(inputPrecoVendido.getText());
            System.out.println("Validou os campos");
            adicionar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Os campos de preço devem conter apenas números inteiros.", "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionar() {
        ProdutosDAO dao = new ProdutosDAO();
        EstoqueDAO daoEst = new EstoqueDAO();

        String nomeProduto = inputNome.getText();
        float precoVendido = Float.parseFloat(inputPrecoVendido.getText());
        int desconto = (int) sPromocao.getValue();
        float valorDesconto = (precoVendido * desconto) / 100;
        float precoFinal = precoVendido - valorDesconto;

        try {

            if (dao.verificarProduto(nomeProduto)) {
                JOptionPane.showMessageDialog(null, "Esse produto já existe!");
                return;
            }

            Connection con = Conexao.conectar();
            String insertProduto = "INSERT INTO produto (nome, categoria, preco, desconto, descontoPorcentagem, precoFinal, imagem) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtProduto = con.prepareStatement(insertProduto, Statement.RETURN_GENERATED_KEYS);
            stmtProduto.setString(1, nomeProduto);
            stmtProduto.setInt(2, idCategoria);
            stmtProduto.setFloat(3, Float.parseFloat(inputPrecoEstoque.getText()));
            stmtProduto.setInt(4, desconto);
            stmtProduto.setFloat(5, valorDesconto);
            stmtProduto.setFloat(6, precoFinal);
            stmtProduto.setBlob(7, fis, tamanho);
            int rowsAffected = stmtProduto.executeUpdate();

            if (rowsAffected == 1) {

                ResultSet generatedKeys = stmtProduto.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idProduto = generatedKeys.getInt(1);

                    daoEst.createEstoque(idProduto, (int) sQtd.getValue());
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao obter o ID do produto.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto.");
            }

            stmtProduto.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto: " + e.getMessage());
        }
    }

    private void carregarFoto() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Selecionar arquivo");
        jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens (*.PNG, *.JPG,*.JPEG)", "png", "jpg", "jpeg"));
        int resultado = jfc.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                fis = new FileInputStream(jfc.getSelectedFile());
                tamanho = (int) jfc.getSelectedFile().length();
                Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(txtImagem.getWidth(), txtImagem.getHeight(), Image.SCALE_SMOOTH);
                txtImagem.setIcon(new ImageIcon(foto));
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtImagem = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        inputNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inputPrecoEstoque = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        inputPrecoVendido = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        sPromocao = new javax.swing.JSpinner();
        sQtd = new javax.swing.JSpinner();
        inputCategoria = new components.ComboBoxSuggestion();
        btn_CadastrarProdutos = new swing.Button();
        btn_CarregarFoto = new swing.Button();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        comboBoxSuggestion1 = new components.ComboBoxSuggestion();
        background1 = new swing.Background2();
        mainPanel = new swing.MainPanel();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(234, 231, 224));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img-produto.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(txtImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 290, 320));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 35, 46));
        jLabel1.setText("Nome:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(45, 35, 46));
        jLabel2.setText("Promocao em porcentagem (Opcional)");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        inputNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNomeActionPerformed(evt);
            }
        });
        jPanel2.add(inputNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 125, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(45, 35, 46));
        jLabel6.setText("Quantidade no estoque:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(45, 35, 46));
        jLabel3.setText("Preço de Estoque:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));
        jPanel2.add(inputPrecoEstoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 125, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(45, 35, 46));
        jLabel7.setText("Preço a ser Vendido (desconsiderar promocao)");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 350, -1));
        jPanel2.add(inputPrecoVendido, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 125, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(45, 35, 46));
        jLabel8.setText("Categoria:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));
        jPanel2.add(sPromocao, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 60, -1));
        jPanel2.add(sQtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 60, -1));

        inputCategoria.setBackground(new java.awt.Color(60, 63, 65));
        inputCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecionar Categoria" }));
        inputCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputCategoriaActionPerformed(evt);
            }
        });
        jPanel2.add(inputCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 170, -1));

        btn_CadastrarProdutos.setText("Cadastrar Produto");
        btn_CadastrarProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CadastrarProdutosActionPerformed(evt);
            }
        });
        jPanel2.add(btn_CadastrarProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, -1, 30));

        btn_CarregarFoto.setText("Carregar Foto");
        btn_CarregarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CarregarFotoActionPerformed(evt);
            }
        });
        jPanel2.add(btn_CarregarFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 110, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Cadastrar Produto");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 150, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 930, 410));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Atualizar Produtos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 440, 140, -1));

        comboBoxSuggestion1.setBackground(new java.awt.Color(60, 63, 65));
        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Promoções" }));
        jPanel1.add(comboBoxSuggestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 440, 360, -1));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Editar Produtos");
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
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1622, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(442, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 1790, 530));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 1090));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNomeActionPerformed

    }//GEN-LAST:event_inputNomeActionPerformed

    private void btn_CadastrarProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CadastrarProdutosActionPerformed
        if (idCategoria == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma categoria");
        } else {
            if (inputNome.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "O nome nao pode estar vazio!");
            } else {
                validarCamposPrecos();
            }
        }
    }//GEN-LAST:event_btn_CadastrarProdutosActionPerformed

    private void btn_CarregarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CarregarFotoActionPerformed
        carregarFoto();
    }//GEN-LAST:event_btn_CarregarFotoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateProdutos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void inputCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputCategoriaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Background2 background1;
    private swing.Button btn_CadastrarProdutos;
    private swing.Button btn_CarregarFoto;
    private components.ComboBoxSuggestion comboBoxSuggestion1;
    private components.ComboBoxSuggestion inputCategoria;
    private javax.swing.JTextField inputNome;
    private javax.swing.JTextField inputPrecoEstoque;
    private javax.swing.JTextField inputPrecoVendido;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private swing.MainPanel mainPanel;
    private javax.swing.JSpinner sPromocao;
    private javax.swing.JSpinner sQtd;
    private javax.swing.JLabel txtImagem;
    // End of variables declaration//GEN-END:variables
}
