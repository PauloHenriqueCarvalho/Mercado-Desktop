/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.JOptionPane;
import model.bean.Usuario;
import model.dao.CarrinhoDAO;
import model.dao.UsuarioDAO;

/**
 *
 * @author Senai
 */
public class TelaLoginUsuario extends javax.swing.JFrame {

    /**
     * Creates new form tela_login
     */
    public TelaLoginUsuario() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Login_Label = new javax.swing.JLabel();
        label____ = new javax.swing.JLabel();
        label2____ = new javax.swing.JLabel();
        input_Email = new javax.swing.JTextField();
        label_image = new javax.swing.JLabel();
        label_email = new javax.swing.JLabel();
        label_senha = new javax.swing.JLabel();
        label_opcao = new javax.swing.JLabel();
        button1 = new swing.Button();
        button2 = new swing.Button();
        inputSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(60, 63, 65));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Login_Label.setFont(new java.awt.Font("Sylfaen", 2, 36)); // NOI18N
        Login_Label.setForeground(new java.awt.Color(255, 255, 255));
        Login_Label.setText("Login");
        jPanel1.add(Login_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 203, 98, -1));

        label____.setForeground(new java.awt.Color(255, 255, 255));
        label____.setText("________________________________________");
        jPanel1.add(label____, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, -1));

        label2____.setForeground(new java.awt.Color(255, 255, 255));
        label2____.setText("________________________________________");
        jPanel1.add(label2____, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, -1, -1));

        input_Email.setBackground(new java.awt.Color(60, 63, 65));
        input_Email.setForeground(new java.awt.Color(255, 255, 255));
        input_Email.setBorder(null);
        input_Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_EmailActionPerformed(evt);
            }
        });
        jPanel1.add(input_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 267, 200, -1));

        label_image.setForeground(new java.awt.Color(255, 255, 255));
        label_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IAGON-Photoroom.png-Photoroom-removebg-preview-removebg-preview.png"))); // NOI18N
        jPanel1.add(label_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 98, -1, -1));

        label_email.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_email.setForeground(new java.awt.Color(255, 255, 255));
        label_email.setText("Email");
        jPanel1.add(label_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 262, 54, -1));

        label_senha.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_senha.setForeground(new java.awt.Color(255, 255, 255));
        label_senha.setText("Senha");
        jPanel1.add(label_senha, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 324, 54, -1));

        label_opcao.setFont(new java.awt.Font("Sylfaen", 2, 16)); // NOI18N
        label_opcao.setForeground(new java.awt.Color(252, 163, 17));
        label_opcao.setText("Se não possui Login, clique aqui para fazer o cadastro");
        label_opcao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_opcaoMouseClicked(evt);
            }
        });
        jPanel1.add(label_opcao, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 356, -1));

        button1.setBackground(new java.awt.Color(33, 33, 33));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Continuar sem Login");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel1.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 180, -1));

        button2.setBackground(new java.awt.Color(33, 33, 33));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Login");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel1.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, 180, -1));

        inputSenha.setBackground(new java.awt.Color(60, 63, 65));
        inputSenha.setForeground(new java.awt.Color(255, 255, 255));
        inputSenha.setBorder(null);
        jPanel1.add(inputSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 240, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void label_opcaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_opcaoMouseClicked
        TelaCadastrarUsuario m = new TelaCadastrarUsuario();
        this.dispose();
        m.setVisible(true);
    }//GEN-LAST:event_label_opcaoMouseClicked

    private void input_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_EmailActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        TelaMenu p = new TelaMenu();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        Usuario u = new Usuario();
        UsuarioDAO dao = new UsuarioDAO();
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        if (dao.login(input_Email.getText(), inputSenha.getText()) == 1) {
            if (carrinhoDAO.verificarCarrinho(Usuario.getIdUsuarioAtual()) == false) {
                carrinhoDAO.criarCarrinho(Usuario.getIdUsuarioAtual());
            }
            TelaMenu menu = new TelaMenu();
            menu.setVisible(true);
            this.dispose();
        } else if (dao.login(input_Email.getText(), inputSenha.getText()) == 2) {
            TelaAdministrador adm = new TelaAdministrador();
            adm.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Email ou Senha incorreto");
        }


    }//GEN-LAST:event_button2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLoginUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Login_Label;
    private swing.Button button1;
    private swing.Button button2;
    private javax.swing.JPasswordField inputSenha;
    private javax.swing.JTextField input_Email;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label2____;
    private javax.swing.JLabel label____;
    private javax.swing.JLabel label_email;
    private javax.swing.JLabel label_image;
    private javax.swing.JLabel label_opcao;
    private javax.swing.JLabel label_senha;
    // End of variables declaration//GEN-END:variables
}
