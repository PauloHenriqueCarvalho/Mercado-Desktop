/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import com.formdev.flatlaf.FlatDarkLaf;
import conexao.Conexao;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.bean.Categoria;
import model.bean.Produto;
import model.dao.CategoriaDAO;
import model.dao.ProdutosDAO;

/**
 *
 * @author Senai
 */
public class EditarProduto extends javax.swing.JFrame {

    Conexao conexao = new Conexao();
    Produto produto = new Produto();
    private FileInputStream fis;
    private int tamanho;
    int idCategoria = -1;

    /**
     * Creates new form EditarProduto
     */
    public EditarProduto() {
        initComponents();
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
    }

    private void validarCamposPrecos() {
        try {
            int precoEstoque = Integer.parseInt(inputPrecoEstoque.getText());
            int precoVendido = Integer.parseInt(inputPrecoVendido.getText());
            adicionar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Os campos de preço devem conter apenas números inteiros.", "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionar() {
        Produto p = new Produto();
        ProdutosDAO dao = new ProdutosDAO();
        float precoVendido = Float.parseFloat(inputPrecoVendido.getText());
        float valorDesconto = (precoVendido * (int) sPromocao.getValue()) / 100;
        float precoFinal = precoVendido - valorDesconto;
        String insert = "INSERT INTO produto (nome, categoria, quantidade, preco, desconto, descontoPorcentagem, precoFinal, imagem) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement(insert);
            stmt.setString(1, inputNome.getText());
            stmt.setInt(2, idCategoria);
            stmt.setInt(3, (int) sQtd.getValue());
            stmt.setFloat(4, Float.parseFloat(inputPrecoEstoque.getText()));
            stmt.setInt(5, (int) sPromocao.getValue());
            stmt.setFloat(6, valorDesconto);
            stmt.setFloat(7, precoFinal);
            stmt.setBlob(8, fis, tamanho);

            int confirma = stmt.executeUpdate();
            if (confirma == 1) {
                JOptionPane.showMessageDialog(null, "Produto Cadastrado");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
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
        btn_EditarProdutos = new swing.Button();
        btn_CarregarFotos = new swing.Button();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(45, 35, 46));

        jPanel2.setBackground(new java.awt.Color(224, 221, 207));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 290, 320));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Nome:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Promocao em porcentagem (Opcional)");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        inputNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNomeActionPerformed(evt);
            }
        });
        jPanel2.add(inputNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 125, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Quantidade no estoque:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, -1, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Preço de Estoque:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, -1, -1));
        jPanel2.add(inputPrecoEstoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 125, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Preço a ser Vendido (desconsiderar promocao)");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));
        jPanel2.add(inputPrecoVendido, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 125, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setText("Categoria:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, -1));
        jPanel2.add(sPromocao, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 60, -1));
        jPanel2.add(sQtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 60, -1));

        inputCategoria.setBackground(new java.awt.Color(45, 35, 46));
        jPanel2.add(inputCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));

        btn_EditarProdutos.setBackground(new java.awt.Color(45, 35, 46));
        btn_EditarProdutos.setForeground(new java.awt.Color(255, 255, 255));
        btn_EditarProdutos.setText("Editar Produtos");
        btn_EditarProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarProdutosActionPerformed(evt);
            }
        });
        jPanel2.add(btn_EditarProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, -1, -1));

        btn_CarregarFotos.setBackground(new java.awt.Color(45, 35, 46));
        btn_CarregarFotos.setForeground(new java.awt.Color(255, 255, 255));
        btn_CarregarFotos.setText("button1");
        btn_CarregarFotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CarregarFotosActionPerformed(evt);
            }
        });
        jPanel2.add(btn_CarregarFotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, -1, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNomeActionPerformed

    }//GEN-LAST:event_inputNomeActionPerformed

    private void btn_EditarProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarProdutosActionPerformed
     validarCamposPrecos();
    }//GEN-LAST:event_btn_EditarProdutosActionPerformed

    private void btn_CarregarFotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CarregarFotosActionPerformed
    carregarFoto();
    }//GEN-LAST:event_btn_CarregarFotosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btn_CarregarFotos;
    private swing.Button btn_EditarProdutos;
    private components.ComboBoxSuggestion inputCategoria;
    private javax.swing.JTextField inputNome;
    private javax.swing.JTextField inputPrecoEstoque;
    private javax.swing.JTextField inputPrecoVendido;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner sPromocao;
    private javax.swing.JSpinner sQtd;
    private javax.swing.JLabel txtImagem;
    // End of variables declaration//GEN-END:variables
}
