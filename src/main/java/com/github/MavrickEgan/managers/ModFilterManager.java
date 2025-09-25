package com.github.MavrickEgan.managers;

import com.github.MavrickEgan.models.Mod;
import com.github.MavrickEgan.modfilter.ModListManager;
import com.github.MavrickEgan.util.IdFileManager;
import com.github.MavrickEgan.util.StringFileManager;

import java.util.*;

public class ModFilterManager {

    String fileName;
    public StringFileManager filterList;

    Map<String,IdFileManager> managers = new HashMap<>();
    IdFileManager currentlySelectedManager;
    IdFileManager hidden;
    IdFileManager favourite;

    int currentModIndex = 0;
    Set<Mod> modList = new HashSet<>();

    List<String> filterOptions = new ArrayList<>();
    String previousSearch = "";
    String previousFilter = "All";

    public ModFilterManager(String fileName) {
        this.fileName = fileName;
        initManagers();
        initFilterList();
    }

    private void initManagers() {
        filterList = new StringFileManager("data/list");
        hidden = new IdFileManager("data/hidden");
        favourite = new IdFileManager("data/favourite");
        managers.put("Favourite", favourite);
        managers.put("Hidden", hidden);
        modList = new ModListManager().getList(fileName, currentModIndex, 7, "", hidden.getList());
    }

    private void initFilterList() {
        filterOptions = new ArrayList<>();
        filterOptions.add("All");
        filterOptions.add("Favourite");
        filterOptions.add("Hidden");
        for (String filterName : getFilterList()) {
            if (!filterName.equalsIgnoreCase("All") && !filterName.equalsIgnoreCase("Favourite") && !filterName.equals("+") && !filterName.equalsIgnoreCase("Hidden")) {
                filterOptions.add(filterName);
                String path = "data/" + filterName;
                IdFileManager manager = new IdFileManager(path);
                managers.put(filterName, manager);
            }
        }
        filterOptions.add("+");
    }
//getters and seets
    public List<String> getFilterList() { return filterList.getList(); }
    public List<String> getFilerOptions() { return filterOptions; }
    public void setCurrentManager(IdFileManager current) { currentlySelectedManager = current; }
    public Map<String,IdFileManager> getManagers(){ return managers; }
    public String getPreviousFilter() { return previousFilter; }
    public String getFileName() { return fileName; }
    public Set<Mod> getModList() { return modList; }

    public void incrementModIndex(int amount) { currentModIndex += amount; }
    public void decrementModIndex(int amount) { currentModIndex -= amount;}
    public void resetModIndex() { currentModIndex = 0; }
    public boolean isModListInvalid() { return (modList == null || modList.isEmpty()); }
    //Events
    public void favouriteModEvent(int id) {
        if (!favourite.contains(id)) favourite.addId(id);
        else favourite.removeId(id);
        updateModListForFilterAndSearch(previousSearch, previousFilter);
    }
    public void hideModEvent(int id) {
        if (!hidden.contains(id)) hidden.addId(id);
        else hidden.removeId(id);
        updateModListForFilterAndSearch(previousSearch, previousFilter);
    }
    public void addModToList(int id, String listName) {
        IdFileManager listManager = managers.get(listName);
        if (listManager != null) {
            if (listManager.contains(id)) { listManager.removeId(id);}
            else { listManager.addId(id); }
        }
    }

    public void selectFilter(String filterName) {
        if (filterName.equals("All")) {
            currentlySelectedManager = null;
        } else {
            currentlySelectedManager = managers.get(filterName);
        }
        resetModIndex();
        previousFilter = filterName;
    }
    //Mod List Update
    public void updateModListForFilterAndSearch(String searchText, String filterName) {
        boolean searchChanged = !searchText.equals(previousSearch);
        boolean filterChanged = !filterName.equals(previousFilter);
        if (searchChanged || filterChanged) {
            resetModIndex();
        }
        previousSearch = searchText;
        previousFilter = filterName;

        if (!filterName.equals("All") && currentlySelectedManager != null) {
            modList = new ModListManager().getList(
                    fileName, currentModIndex, 7, currentlySelectedManager.getList(), searchText);
        } else {
            modList = new ModListManager().getList(
                    fileName, currentModIndex, 7, searchText, hidden.getList());
        }
        // invalid list
        if (isModListInvalid() && currentModIndex > 0) {
            decrementModIndex(7);
            if (currentModIndex < 0) resetModIndex();
            modList = new ModListManager().getList(
                    fileName, currentModIndex, 7, searchText, hidden.getList());
        }
    }
}
