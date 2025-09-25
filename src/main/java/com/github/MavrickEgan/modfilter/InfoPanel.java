package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InfoPanel extends JPanel {

    public InfoPanel(String name, String description) {
        setBackground(Util.background);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel namelabel = new JLabel(name);
        namelabel.setFont(Util.title);
        namelabel.setForeground(Util.headline);

        add(namelabel);
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(Util.text);
        descriptionLabel.setForeground(Util.headline);
        descriptionLabel.setForeground(Util.paragraph);

        add(descriptionLabel);
        setBorder(new EmptyBorder(0,20,0,0));
    }

//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Util.extra);
//        g.fillRect(0, 0, getWidth(), 2);
//    }
}
