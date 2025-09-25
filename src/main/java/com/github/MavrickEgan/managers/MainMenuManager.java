package com.github.MavrickEgan.managers;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainMenuManager {
    private String[] platformOptions;
    private String[] modloaderOptions;
    private String[] versionOptions;

    public MainMenuManager() {
        initData();
    }

    private void initData() {
        File folder = new File(Paths.get("data").toUri());
        Set<String> platformSet = new HashSet<>();
        Set<String> modloaderSet = new HashSet<>();
        Set<String> versionSet = new HashSet<>();

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            String name = file.getName();
            if (name.endsWith(".csv")) {
                String baseName = name.substring(0, name.length() - 4);
                String[] parts = baseName.split("-");
                if (parts.length == 3) {
                    platformSet.add(parts[0]);
                    modloaderSet.add(parts[1]);
                    versionSet.add(parts[2]);
                }
            }
        }

        platformOptions = platformSet.toArray(new String[0]);
        modloaderOptions = modloaderSet.toArray(new String[0]);
        versionOptions = versionSet.toArray(new String[0]);
    }

    public String[] getPlatformOptions(){
        return platformOptions;
    }
    public String[] getModLoaderOptions(){
        return modloaderOptions;
    }
    public String[] getVersionOptions(){
        return versionOptions;
    }
}
