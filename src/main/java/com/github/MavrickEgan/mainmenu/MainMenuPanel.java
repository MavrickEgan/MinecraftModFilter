package com.github.MavrickEgan.mainmenu;

import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.eventbus.event.StartModListEvent;
import com.github.MavrickEgan.managers.MainMenuManager;
import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuPanel extends JPanel {
    MainMenuManager manager;
    ContinueButton contButton;

    public MainMenuPanel(MainMenuManager manager, EventBus bus) {
        this.manager = manager;
        setOpaque(false);
        setLayout(new BorderLayout());
        initPanel(bus);
        activateKeybinds();
    }

    public void initPanel(EventBus bus){
        initTitlePanel();
        initContentsPanel(bus);
    }
    private void initTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setOpaque(false);
        JLabel title = getTitle();
        titlePanel.add(Box.createVerticalStrut(125));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);
    }
    private JLabel getTitle() {
        JLabel title = new JLabel("Explore Mods");
        title.setFont(Util.title);
        title.setForeground(Util.headline);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        return title;
    }


    private void initContentsPanel(EventBus bus) {
            JPanel contentPanel = createContentPanel();
            addComboBoxSection(contentPanel,bus);

    }



    private void addComboBoxSection(JPanel contentPanel, EventBus bus) {
        JLabel platformTitle = getSubTitleLabel("Mod Platform");
        ComboBox<String> platformComboBox = new ComboBox<>(manager.getPlatformOptions());
        JLabel modLoaderTitle = getSubTitleLabel("Mod Loader");
        ComboBox<String> modloaderComboBox = new ComboBox<>(manager.getModLoaderOptions());
        JLabel versionTitle = getSubTitleLabel("Minecraft Version");
        ComboBox<String> versionsComboBox = new ComboBox<>(manager.getVersionOptions());

        contentPanel.add((Box.createVerticalStrut(100)));
        contentPanel.add(platformTitle);
        contentPanel.add(platformComboBox);
        contentPanel.add((Box.createVerticalStrut(50)));
        contentPanel.add(modLoaderTitle);
        contentPanel.add(modloaderComboBox);
        contentPanel.add((Box.createVerticalStrut(50)));
        contentPanel.add(versionTitle);
        contentPanel.add(versionsComboBox);
        contentPanel.add((Box.createVerticalStrut(75)));
        contButton = new ContinueButton("Start Exploring Mods");
        contButton.addActionListener(e -> {
            bus.post(new StartModListEvent(platformComboBox.getSelectedItem().toString(), modloaderComboBox.getSelectedItem().toString(), versionsComboBox.getSelectedItem().toString()));
        });
        contButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(contButton);
        add(contentPanel, BorderLayout.CENTER);

    }
    private JLabel getSubTitleLabel(String name) {
        JLabel label = new JLabel(name);
        label.setFont(Util.title);
        label.setForeground(Util.headline);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(25,0,0,0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintBackgroundAndBox((Graphics2D) g);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
    }

    private void paintBackgroundAndBox(Graphics2D g2d) {
        g2d.setColor(Util.background);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int boxWidth = 700;
        int boxHeight = 700;
        int x = (getWidth() - boxWidth) / 2;
        int y = (getHeight() - boxHeight) / 2 - 50;

        g2d.setColor(Util.panel);
        g2d.fillRoundRect(x, y, boxWidth, boxHeight, 50, 50);

        g2d.setColor(Util.border);
        g2d.drawRoundRect(x, y, boxWidth, boxHeight, 50, 50);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Util.background);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
    



    private void activateKeybinds() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    SwingUtilities.invokeLater(() -> {
                        for (ActionListener al : contButton.getActionListeners()) {
                            al.actionPerformed(new ActionEvent(contButton, ActionEvent.ACTION_PERFORMED, "link"));
                        }
                    });
                }
                return false;
            }
        });
    }



}
