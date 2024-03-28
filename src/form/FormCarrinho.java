package form;

import components.Item;
import event.EventItem;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.SwingUtilities;
import model.bean.ModelItem;
import model.bean.ProdutoCarrinho;
import model.bean.Usuario;
import model.dao.ProdutoCarrinhoDAO;
import swing.ScrollBar;

public class FormCarrinho extends javax.swing.JPanel {
    int quantidade = 0;
    public void setEvent(EventItem event) {
        this.event = event;
    }

    private EventItem event;

    public void removeAllItems() {
        panelItem.removeAll();
        revalidate();
        repaint();
    }

    public FormCarrinho() {
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
        labelQuantidade = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnDiminuir = new swing.Button();

        setOpaque(false);

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelItem);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbItemName.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbItemName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbItemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 155, 230, -1));

        lbPrice.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbPrice.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 173, 102, 32));

        labelQuantidade.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        labelQuantidade.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(labelQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 213, 54, 29));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 139, 280, 10));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 338, -1, -1));

        btnDiminuir.setBackground(new java.awt.Color(255, 0, 0));
        btnDiminuir.setText("Remover Produto");
        btnDiminuir.setBorderPainted(false);
        btnDiminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiminuirActionPerformed(evt);
            }
        });
        jPanel1.add(btnDiminuir, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 293, 135, 36));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1327, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDiminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiminuirActionPerformed
        ProdutoCarrinhoDAO dao = new ProdutoCarrinhoDAO();
        dao.removerProdutoDoCarrinho(idItemSelecionado(), Usuario.getIdUsuarioAtual());
        
        
    }//GEN-LAST:event_btnDiminuirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnDiminuir;
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
