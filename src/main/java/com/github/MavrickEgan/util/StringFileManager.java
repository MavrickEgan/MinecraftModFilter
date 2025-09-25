package com.github.MavrickEgan.util;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringFileManager {

    private List<String> stringList = new ArrayList<>();
    private String fileName;
    /**
     * @param filename name of the file in directory
     * */
    public StringFileManager(String filename){
        this.fileName = filename;
        obtainList();
    }

    public void obtainList() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line != null && !line.isBlank()) {
                String[] tokens = line.trim().split("\\s+");
                stringList.addAll(Arrays.asList(tokens));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addString(String str) {
        if (!stringList.contains(str)) {
            stringList.add(str);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(str + " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean contains(String str) {
        return stringList.contains(str);
    }

    public void removeString(String str) {
        if (stringList.remove(str)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                for (String existing : stringList) {
                    writer.write(existing + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getList() {
        return this.stringList;
    }
}
