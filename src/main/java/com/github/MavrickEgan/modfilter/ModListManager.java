package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.models.Mod;
import com.opencsv.CSVReaderHeaderAware;

import java.io.FileReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModListManager {

    public ModListManager() {

    }

    public Set<Mod> getList(String fileName, int start, int amount, String searchText, List<Integer> hidden) {
        Set<Mod> modList = new LinkedHashSet<>();
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader("data/" + fileName))) {
            String[] fields;
            int matchingCount = 0;
            while ((fields = reader.readNext()) != null) {
                if (fields.length < 5) continue;

                String name = fields[1].trim();
                int id = Integer.parseInt(fields[0].trim());
                if (searchText == null || searchText.isBlank() || name.toLowerCase().contains(searchText.toLowerCase())) {
                    if (hidden.contains(id)) {
                        continue;
                    }

                    if (matchingCount++ < start) continue;

                    String link = fields[2].trim();
                    String art = fields[3].trim();
                    String description = fields[4].trim();
                    Mod mod = new Mod(id, name, link, art, description);
                    modList.add(mod);

                    if (modList.size() >= amount) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modList;
    }

    public Set<Mod> getList(String fileName, int start, int amount, List<Integer> idList, String searchText) {
        Set<Mod> modList = new LinkedHashSet<>();
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader("data/" + fileName))) {
            String[] fields;
            int matchingCount = 0;
            while ((fields = reader.readNext()) != null) {
                if (fields.length < 5) continue;

                String name = fields[1].trim();
                int id = Integer.parseInt(fields[0].trim());
                if (!idList.contains(id)) continue;
                if (searchText == null || searchText.isBlank() || name.toLowerCase().contains(searchText.toLowerCase())) {
                    if (matchingCount++ < start) continue;

                    String link = fields[2].trim();
                    String art = fields[3].trim();
                    String description = fields[4].trim();
                    Mod mod = new Mod(id, name, link, art, description);
                    modList.add(mod);

                    if (modList.size() >= amount) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modList;
    }
}
