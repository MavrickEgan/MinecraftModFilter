package com.github.MavrickEgan;

import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.mainmenu.MainMenuPanel;
import com.github.MavrickEgan.managers.MainMenuManager;
import com.github.MavrickEgan.managers.ModFilterManager;
import com.github.MavrickEgan.modfilter.ModFilterPanel;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        super("Mod Filter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080,1920);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/assets/icon.png")).getImage());

    }

    public void showMainMenu(MainMenuManager manager, EventBus bus) {
        getContentPane().removeAll();
        setContentPane(new MainMenuPanel(manager, bus));
        revalidate();
        repaint();
    }
    public void showModList(ModFilterManager manager, EventBus bus) {
        getContentPane().removeAll();
        setContentPane(new ModFilterPanel(manager,bus));
        revalidate();
        repaint();
    }

}
