package form;

import components.Item;
import event.EventItem;
import frames.TelaEditarProduto;
import internalframes.InternalEstoque;
import internalframes.InternalProduto;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.bean.Estoque;
import model.bean.ModelItem;
import model.bean.Produto;
import model.bean.ProdutoCarrinho;
import model.dao.CarrinhoDAO;
import model.dao.EstoqueDAO;
import model.dao.ProdutoCarrinhoDAO;
import model.dao.ProdutosDAO;
import swing.ScrollBar;

public class FormEstoque extends javax.swing.JPanel {

    int quantidade = 0;
    int produtoID = 0;
    
    public void setEvent(EventItem event) {
        this.event = event;
    }

    private EventItem event;

    public void removeAllItems() {
        panelItem.removeAll();
        revalidate();
        repaint();
    }

    public FormEstoque() {
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
        labelQuantidade.setText("" + data.getDescription());
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        lbPrice.setText(df.format(data.getPrice()));
        quantidade = data.getDescription();

        produtoID = idItemSelecionado();
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
        labelQuantidade = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnAdicionar = new swing.Button();
        jPanel2 = new javax.swing.JPanel();
        btnDiminuir = new swing.Button();
        button1 = new swing.Button();

        setOpaque(false);

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelItem.setBackground(new java.awt.Color(60, 63, 65));
        scroll.setViewportView(panelItem);

        jPanel1.setOpaque(false);

        lbItemName.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbItemName.setForeground(new java.awt.Color(255, 255, 255));

        lbPrice.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbPrice.setForeground(new java.awt.Color(255, 255, 255));

        labelQuantidade.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        labelQuantidade.setForeground(new java.awt.Color(255, 255, 255));

        btnAdicionar.setBackground(new java.awt.Color(51, 255, 0));
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDiminuir.setBackground(new java.awt.Color(255, 0, 0));
        btnDiminuir.setText("Diminuir");
        btnDiminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiminuirActionPerformed(evt);
            }
        });

        button1.setText("Editar Produto");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnDiminuir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)))))
                        .addGap(60, 60, 60))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(labelQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbItemName)
                .addGap(18, 18, 18)
                .addComponent(lbPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(labelQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDiminuir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1317, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        Estoque e = new Estoque();
        EstoqueDAO dao = new EstoqueDAO();
        e = dao.obterEstoque(idItemSelecionado());
        dao.aumentarQuantidade(idItemSelecionado(), e.getQuantidade());
        e = dao.obterEstoque(idItemSelecionado());
        labelQuantidade.setText(("" + e.getQuantidade()));


    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnDiminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiminuirActionPerformed
        Estoque e = new Estoque();
        EstoqueDAO dao = new EstoqueDAO();
        System.out.println(idItemSelecionado());
        e = dao.obterEstoque(idItemSelecionado());
        if (e != null) {
            System.out.println(e.getQuantidade());
            if (e.getQuantidade() == 0) {
                JOptionPane.showMessageDialog(null, "Quantidade Mínima");
            } else {
                dao.diminuirQuantidade(idItemSelecionado(), e.getQuantidade());
                e = dao.obterEstoque(idItemSelecionado());
            }
            labelQuantidade.setText(String.valueOf(e.getQuantidade()));
        } else {
            System.out.println("Estoque não encontrado para o produto com ID: " + idItemSelecionado());
        }
    }//GEN-LAST:event_btnDiminuirActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        TelaEditarProduto e = new TelaEditarProduto(produtoID);
        if(produtoID != 0 ) {
            e.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado!s");
        }
    }//GEN-LAST:event_button1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnAdicionar;
    private swing.Button btnDiminuir;
    private swing.Button button1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelQuantidade;
    private javax.swing.JLabel lbItemName;
    private javax.swing.JLabel lbPrice;
    private swing2.PanelItem panelItem;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
