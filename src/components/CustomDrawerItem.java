/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author paulo
 */
public class CustomDrawerItem extends JLabel {

    private Color textColor;

    public CustomDrawerItem(String text, Icon icon) {
        super(text, icon, JLabel.LEFT);
        setOpaque(false); // Defina como transparente para manter o fundo
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setForeground(Color.WHITE); // Defina a cor padrão do texto
        this.textColor = Color.WHITE; // Mantenha o controle da cor atual do texto
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        setForeground(color);
    }

    public Color getTextColor() {
        return textColor;
    }
}

