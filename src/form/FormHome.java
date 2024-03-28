package form;

import components.Item;
import event.EventItem;
import frames.TelaCarrinho;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.bean.ModelItem;
import model.bean.ProdutoCarrinho;
import model.bean.Usuario;
import model.dao.CarrinhoDAO;
import model.dao.EstoqueDAO;
import model.dao.ProdutoCarrinhoDAO;
import swing.ScrollBar;

public class FormHome extends javax.swing.JPanel {

    public void setEvent(EventItem event) {
        this.event = event;
    }

    private EventItem event;

    public void removeAllItems() {
        panelItem.removeAll();
        revalidate();
        repaint();
    }

    public FormHome() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
    }

    public void addItem(ModelItem data) {
        Item item = new Item();
        item.setData(data);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    event.itemClick(item, data);
                }
            }
        });
        panelItem.add(item);
        panelItem.repaint();
        panelItem.revalidate();
    }

    public boolean validarQuantidadeEstoque(int idProduto, int quantidadeSolicitada) {
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        int quantidadeDisponivel = estoqueDAO.consultarQuantidadeDisponivel(idProduto);
        return quantidadeDisponivel >= quantidadeSolicitada;
    }

    public void setSelected(Component item) {
        for (Component com : panelItem.getComponents()) {
            Item i = (Item) com;
            if (i.isSelected()) {
                i.setSelected(false);
            }
        }
        ((Item) item).setSelected(true);
    }

    private int idItemSelecionado() {
        for (Component com : panelItem.getComponents()) {
            Item item = (Item) com;
            if (item.isSelected()) {
                return item.getData().getItemID();
            }
        }
        return -1;
    }

    public void showItem(ModelItem data) {
        lbItemName.setText(data.getItemName());
        lbBrand.setText(data.getBrandName());
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        lbPrice.setText(df.format(data.getPrice()));

        int produtoID = idItemSelecionado();
        if (produtoID != -1) {
            System.out.println("Produto selecionado:");
            System.out.println("ID: " + produtoID);
            System.out.println("Nome: " + data.getItemName());
            System.out.println("Descrição: " + data.getDescription());
            System.out.println("Marca: " + data.getBrandName());
            System.out.println("Preço: " + df.format(data.getPrice()));
        } else {
            System.out.println("Nenhum produto selecionado.");
        }
    }

    public Point getPanelItemLocation() {
        Point p = scroll.getLocation();
        return new Point(p.x, p.y - scroll.getViewport().getViewPosition().y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        panelItem = new swing2.PanelItem();
        jPanel1 = new javax.swing.JPanel();
        lbItemName = new javax.swing.JLabel();
        lbPrice = new javax.swing.JLabel();
        lbBrand = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        button1 = new swing.Button();
        jPanel2 = new javax.swing.JPanel();
        spinnerQuantidade = new javax.swing.JSpinner();

        setOpaque(false);

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelItem);

        jPanel1.setOpaque(false);

        lbItemName.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbItemName.setForeground(new java.awt.Color(76, 76, 76));
        lbItemName.setText("Item Name");

        lbPrice.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbPrice.setForeground(new java.awt.Color(76, 76, 76));
        lbPrice.setText("$0.00");

        lbBrand.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lbBrand.setForeground(new java.awt.Color(76, 76, 76));
        lbBrand.setText("Brand");

        button1.setBackground(new java.awt.Color(51, 255, 0));
        button1.setText("Adicionar ao Carrinho");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbBrand)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbPrice)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(spinnerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbItemName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPrice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbBrand)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(292, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1309, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        int idProduto = idItemSelecionado();
        ProdutoCarrinhoDAO carrinhoDAO = new ProdutoCarrinhoDAO();

        if ((int) spinnerQuantidade.getValue() <= 0) {
            JOptionPane.showMessageDialog(null, "Adicione pelo menos 1 produto");
        } else {
            // Verificar se a quantidade solicitada está disponível no estoque
            int quantidadeSolicitada = (int) spinnerQuantidade.getValue();
            if (!validarQuantidadeEstoque(idProduto, quantidadeSolicitada)) {
                JOptionPane.showMessageDialog(null, "A quantidade solicitada não está disponível no estoque.");
                return; // Encerrar o método aqui para evitar adicionar o produto ao carrinho
            }

            if (carrinhoDAO.verificarItemNoCarrinho(Usuario.getIdUsuarioAtual(), idProduto)) {
                JOptionPane.showMessageDialog(null, "Já existe esse produto no carrinho!");
            } else {
                carrinhoDAO.adicionarProdutoAoCarrinho(idProduto, Usuario.getIdUsuarioAtual(), quantidadeSolicitada);
                Object[] options = {"Ir para o carrinho", "Continuar comprando"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Produto adicionado ao carrinho com sucesso!",
                        "Sucesso",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == JOptionPane.YES_OPTION) {
                    TelaCarrinho c = new TelaCarrinho();
                    c.setVisible(true);
                    SwingUtilities.getWindowAncestor(this).setVisible(false);

                } else {
                    System.out.println("Continuando a comprar...");
                }
                spinnerQuantidade.setValue(0);
            }
        }

    }//GEN-LAST:event_button1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button button1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbBrand;
    private javax.swing.JLabel lbItemName;
    private javax.swing.JLabel lbPrice;
    private swing2.PanelItem panelItem;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JSpinner spinnerQuantidade;
    // End of variables declaration//GEN-END:variables
}
