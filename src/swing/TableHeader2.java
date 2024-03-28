package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class TableHeader2 extends JLabel {

    public TableHeader2(String text) {
        super(text);
        setOpaque(true);
        setBackground(new Color(164,22,26));
        setFont(new Font("sansserif", 1, 12));
        setForeground(Color.white);
        setBorder(new EmptyBorder(10, 5, 10, 5));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(new Color(230, 230, 230));
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}
