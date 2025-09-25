package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.eventbus.event.ReturnToMenuEvent;
import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReturnButton extends JButton {
    public ReturnButton(String name, EventBus bus) {
        super(name);
        setFont(Util.buttonBig);
        Dimension size = new Dimension(125,135);
        setMinimumSize(size);
        setMaximumSize(size);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        addActionListener(e -> {
            bus.post(new ReturnToMenuEvent());
        });
        setBorderPainted(false);
        setRolloverEnabled(false);
        setBorder(new EmptyBorder(10,20,0,0));

    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int boxWidth =getWidth();
        int boxHeight = getHeight();
        int x = (getWidth() - boxWidth) / 2;
        int y = (getHeight() - boxHeight) / 2;
        g2d.setColor(Util.button);
        g2d.fillRoundRect(x,y,boxWidth,boxHeight,15,15);

        g2d.setColor(Util.buttonText);
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        // Draw text
        g2d.drawString(text, textX, textY);



        g2d.dispose();

    }
}