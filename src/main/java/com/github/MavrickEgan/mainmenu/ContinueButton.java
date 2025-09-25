package com.github.MavrickEgan.mainmenu;

import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import java.awt.*;

public class ContinueButton extends JButton {
    public ContinueButton(String name) {
        super(name);
        setForeground(Util.buttonText);
        setFont(Util.buttonBig);
        Dimension size = new Dimension(600,50);
        setMinimumSize(size);
        setMaximumSize(size);
        setBorderPainted(false);
        setRolloverEnabled(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int boxWidth =getWidth();
        int boxHeight = getHeight();
        int x = (getWidth() - boxWidth) / 2;
        int y = (getHeight() - boxHeight) / 2;
        g2d.setColor(Util.button);
        g2d.fillRoundRect(x,y,boxWidth,boxHeight,50,50);


        g2d.setColor(Util.buttonText);
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;
        g2d.drawString(text, textX, textY);

        g2d.dispose();

    }

}