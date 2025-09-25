package com.github.MavrickEgan.mainmenu;

import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class ComboBox<E> extends JComboBox<E> {
    public ComboBox(E[] items) {
        super(items);
        setFont(Util.text);
        setForeground(Util.buttonText);
        setBackground(Util.secondary);
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(400,40));
        setUI(new RoundedComboBoxUI());
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        });
    }
    private class RoundedComboBoxUI extends BasicComboBoxUI {

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        }
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 20;
            int width = c.getWidth();
            int height = c.getHeight();

            g2.setColor(Util.option);
            g2.fillRoundRect(0, 0, width, height, arc, arc);
            g2.setColor(Util.optionBorder);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, width - 1, height - 1, arc, arc);

            g2.dispose();
            super.paint(g, c);
        }

        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = new JButton();
            arrowButton.setBorder(BorderFactory.createEmptyBorder());
            arrowButton.setContentAreaFilled(false);
            arrowButton.setFocusable(false);
            arrowButton.setOpaque(false);
            arrowButton.setIcon(UIManager.getIcon("ComboBox.arrowIcon"));
            return arrowButton;
        }
    }

}
