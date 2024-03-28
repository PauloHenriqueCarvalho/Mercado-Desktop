/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import conexao.Conexao;
import form.FormProdutos;
import internalframes.InternalEstoque;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.bean.Categoria;
import model.bean.ImagemData;
import model.bean.ModelItem;
import model.bean.Produto;
import model.dao.CategoriaDAO;
import model.dao.EstoqueDAO;
import model.dao.ProdutosDAO;
import org.jdesktop.animation.timing.Animator;

/**
 *
 * @author Senai
 */
public class TelaEditarProduto extends javax.swing.JFrame {

    Conexao conexao = new Conexao();
    Produto produto = new Produto();
    private FileInputStream fis;
    private int tamanho;
    int idCategoria = -1;
    private FormProdutos home;
    private Animator animator;
    private Point animatePoint;
    private ModelItem itemSelected;
    private int idCate = -1;

    /**
     * Creates new form EditarProduto
     */
    public TelaEditarProduto(int idProduto) {
        initComponents();
        ProdutosDAO daos = new ProdutosDAO();
        produto = daos.readProduto(idProduto);
        inputNome.setText("" + produto.getNome());
        inputPreco.setText("" + produto.getPreco());
        inputPromo.setText("" + produto.getDesconto());
        inputVenda.setText("" + produto.getPrecoFinal());
        produto.setCategoria(-1);
        produto.setIdProduto(idProduto);

        ImageIcon imagemProduto = daos.getImagemDoBancoDeDados(idProduto);
        Blob blob;
        if (imagemProduto != null) {
            txtImagem.setIcon(imagemProduto);
            Image foto = imagemProduto.getImage();

            try {
                blob = imageToBlob(foto);

                produto.setIcon(blob);
                foto = foto.getScaledInstance(txtImagem.getWidth(), txtImagem.getHeight(), Image.SCALE_SMOOTH);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            txtImagem.setIcon(imagemProduto);
            Image foto = imagemProduto.getImage();
            try {
                blob = imageToBlob(foto);
                produto.setIcon(blob);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            foto = foto.getScaledInstance(txtImagem.getWidth(), txtImagem.getHeight(), Image.SCALE_SMOOTH);

        }

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
                        produto.setCategoria(idCategoriaSelecionada);
                        break;
                    }
                }

                System.out.println("ID da categoria selecionada: " + idCategoriaSelecionada);
            }
        });
    }

    public Blob imageIconToBlob(ImageIcon imageIcon) throws SQLException {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(txtImagem.getWidth(), txtImagem.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);

            byte[] imageBytes = outputStream.toByteArray();

            Blob blob = new SerialBlob(imageBytes);
            return blob;
        } catch (IOException e) {
            throw new SQLException("Erro ao converter ImageIcon para Blob: " + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Blob imageToBlob(Image image) throws SQLException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(image, 0, 0, null);

            ImageIO.write(bufferedImage, "png", outputStream);

            byte[] imageBytes = outputStream.toByteArray();

            Blob blob = new SerialBlob(imageBytes);
            return blob;
        } catch (IOException e) {
            throw new SQLException("Erro ao converter imagem para Blob: " + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void validarCamposPrecos() {
        try {
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
        int desconto = produto.getDesconto();
        float precoVendido = Float.parseFloat(inputVenda.getText());
        float valorDesconto = (precoVendido * desconto) / 100;
        float precoFinal = precoVendido - valorDesconto;

        try {
            Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement("UPDATE produto SET nome = ?, categoria = ?, preco = ?, desconto = ?, descontoPorcentagem = ?, precoFinal = ?, imagem = ? WHERE idProduto = ?");
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getCategoria());
            stmt.setFloat(3, produto.getPreco());
            stmt.setInt(4, produto.getDesconto());
            stmt.setFloat(5, produto.getDescontoPorcentagem());
            stmt.setFloat(6, produto.getPrecoFinal());
            stmt.setBlob(7, fis, tamanho);
            stmt.setInt(8, produto.getIdProduto());
            System.out.println("ID DO PRODUTO UODATE: " + produto.getIdProduto());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso! (Atualiza os produtos)");
            stmt.close();
            con.close();
            this.dispose();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        inputNome = new javax.swing.JTextField();
        inputPreco = new javax.swing.JTextField();
        btnCad = new javax.swing.JButton();
        comboBoxSuggestion1 = new components.ComboBoxSuggestion();
        jLabel4 = new javax.swing.JLabel();
        inputVenda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        inputPromo = new javax.swing.JTextField();
        txtImagem = new javax.swing.JLabel();
        button1 = new swing.Button();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setBackground(new java.awt.Color(234, 231, 224));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Nome:");
        panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Preço Custo:");
        panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Categoria:");
        panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        inputNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNomeActionPerformed(evt);
            }
        });
        panel.add(inputNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 160, -1));
        panel.add(inputPreco, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 160, -1));

        btnCad.setText("Confirmar");
        btnCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadActionPerformed(evt);
            }
        });
        panel.add(btnCad, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 150, -1));

        comboBoxSuggestion1.setBackground(new java.awt.Color(60, 63, 65));
        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione uma Categoria" }));
        panel.add(comboBoxSuggestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 160, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Preço Venda:");
        panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 110, -1));
        panel.add(inputVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 160, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Promocão:");
        panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 90, -1));
        panel.add(inputPromo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 160, -1));

        txtImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img-produto.png"))); // NOI18N
        txtImagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel.add(txtImagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 280, 150));

        button1.setText("Carregar imagem");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        panel.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 120, -1));

        jButton2.setBackground(new java.awt.Color(255, 0, 51));
        jButton2.setText("Fechar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, -1, -1));

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 700, 280));

        jPanel1.setBackground(new java.awt.Color(234, 231, 224));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 380));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNomeActionPerformed

    }//GEN-LAST:event_inputNomeActionPerformed

    private void btnCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadActionPerformed
        if (inputNome.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "O nome nao pode estar vazio!");
        } else {
            produto.setNome(inputNome.getText());
            produto.setPreco(Float.parseFloat(inputPreco.getText()));
            produto.setDesconto((int) Float.parseFloat(inputPreco.getText()));
            produto.setPrecoFinal(Float.parseFloat(inputVenda.getText()));
            produto.setPreco(Float.parseFloat(inputPreco.getText()));

            if (produto.getCategoria() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma categoria");
            } else {
                System.out.println("Nome do produto: " + produto.getNome());
                System.out.println("Preço: " + produto.getPreco());
                System.out.println("Desconto: " + produto.getDesconto());
                System.out.println("Preço final: " + produto.getPrecoFinal());
                validarCamposPrecos();
                inputNome.setText("" + produto.getNome());
                inputPreco.setText("" + produto.getPreco());
                inputPromo.setText("" + produto.getDesconto());
                inputVenda.setText("" + produto.getPrecoFinal());
            }
        }


    }//GEN-LAST:event_btnCadActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        carregarFoto();
    }//GEN-LAST:event_button1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCad;
    private swing.Button button1;
    private components.ComboBoxSuggestion comboBoxSuggestion1;
    private javax.swing.JTextField inputNome;
    private javax.swing.JTextField inputPreco;
    private javax.swing.JTextField inputPromo;
    private javax.swing.JTextField inputVenda;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel txtImagem;
    // End of variables declaration//GEN-END:variables
}
