package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.eventbus.event.AddListEvent;
import com.github.MavrickEgan.eventbus.event.FavouriteModEvent;
import com.github.MavrickEgan.eventbus.event.HideModEvent;
import com.github.MavrickEgan.models.Mod;
import com.github.MavrickEgan.util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ModPanel extends JPanel {
    private JLabel artLabel;
    public ModPanel(Mod mod, EventBus bus) {

        setLayout(new BorderLayout());
        setBackground(Util.background);
        setPreferredSize(new Dimension(100, 100));
        setMaximumSize(new Dimension(1920,110));

        artLabel = new JLabel("Loading");
        add(artLabel, BorderLayout.WEST);

        InfoPanel info = new InfoPanel(mod.getName(), mod.getDescription());
        add(info, BorderLayout.CENTER);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // bus.post(new SelectedModEvent(mod));
            }
        });
        ExperimentButtonPanel(bus, mod);

        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground(){
                try {
                    String imageUrl = mod.getArt();
                    BufferedImage img = ImageIO.read(new URL(imageUrl));
                    if (img != null) {
                        Image scaled = img.getScaledInstance(96,96,Image.SCALE_SMOOTH);
                        return new ImageIcon(scaled);
                    } else {
                        System.err.println("ImageIO could not decode the image");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }/*
                try {
                    return new ImageIcon(new URL(mod.getArt()));
                } catch (Exception e) {
                    return null;
                }*/
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        artLabel.setIcon(icon);
                        artLabel.setText(null);
                    } else {
                        artLabel.setText("Image not available");
                    }
                } catch (Exception e) {
                    artLabel.setText("Failed to load image");
                }
            }
        }.execute();
    }

    private void ExperimentButtonPanel(EventBus bus, Mod mod) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);
        JButton link = getBottomButton("Link");
        link.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URL(mod.getLink()).toURI());
            } catch (Exception E) {
                E.printStackTrace();
            }
        });
        JButton favourite = getBottomButton("Favourite");
        favourite.addActionListener(e -> {
            bus.post(new FavouriteModEvent(mod.getId()));
        });
        JButton hide = getBottomButton("Hide");
        hide.addActionListener(e -> {
            bus.post(new HideModEvent(mod.getId()));
        });
        JButton list = getBottomButton("Add to list");
        list.addActionListener(e -> {
            bus.post(new AddListEvent(mod.getId()));
        });
        buttonPanel.add(link);
        buttonPanel.add(favourite);
        buttonPanel.add(hide);
        buttonPanel.add(list);

        add(buttonPanel, BorderLayout.EAST);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private JButton getBottomButton(String name) {
        JButton button = new JButton(name);
        button.setForeground(Util.buttonText);
        button.setBackground(Util.button);
        button.setFont(Util.subtitle);
        button.setPreferredSize(new Dimension(150,50));
        return button;
    }

}