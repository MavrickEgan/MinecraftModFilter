package com.github.MavrickEgan;

import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.eventbus.event.ReturnToMenuEvent;
import com.github.MavrickEgan.eventbus.event.StartModListEvent;
import com.github.MavrickEgan.managers.MainMenuManager;
import com.github.MavrickEgan.managers.ModFilterManager;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EventBus bus = new EventBus();
                createDataFolder();
                MainFrame frame = new MainFrame();
                frame.showMainMenu(new MainMenuManager(), bus);
                frame.setVisible(true);
                bus.subscribe(StartModListEvent.class, e -> {
                    File folder = new File(Paths.get("data").toUri());
                    File target = new File(folder,e.fileName);
                    if(target.exists()) {
                        frame.showModList(new ModFilterManager(e.fileName),bus);
                    }
                });
                bus.subscribe(ReturnToMenuEvent.class, e -> {
                    frame.showMainMenu(new MainMenuManager(), bus);
                });

            }
        });
    }
    private static void createDataFolder() {
        try {
            File jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
            File dataFolder = new File(jarDir, "data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
        } catch (Exception e) {e.printStackTrace(); }
    }


}