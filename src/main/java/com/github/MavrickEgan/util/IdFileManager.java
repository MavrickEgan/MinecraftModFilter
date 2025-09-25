package com.github.MavrickEgan.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IdFileManager {

    private List<Integer> idList = new ArrayList<>();
    private String fileName;
    /**
     * @param filename name of the file in directory
     * */
    public IdFileManager(String filename){
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
                for (String token : tokens) {
                    try {
                        int id = Integer.parseInt(token);
                        idList.add(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addId(int id){
        if(!idList.contains(id)) {
            idList.add(id);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(id + " ");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean contains(int id) {
        return(idList.contains(id));
    }
    public void removeId(int id){
        if(idList.remove((Integer) id)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                for (int existingId : idList) {
                    writer.write(existingId + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Integer> getList() {
        return this.idList;
    }
}
