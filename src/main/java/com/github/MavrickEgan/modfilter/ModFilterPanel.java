package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.eventbus.event.*;
import com.github.MavrickEgan.managers.ModFilterManager;
import com.github.MavrickEgan.models.Mod;
import com.github.MavrickEgan.util.IdFileManager;
import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ModFilterPanel extends JPanel {
    private final ModFilterManager manager;
    private final String fileName;

    private JPanel mainPanel;
    private JPanel bottom;

    private final FilterButtonBar filterBar;
    private final SearchBar searchBar = new SearchBar();

    public ModFilterPanel(ModFilterManager manager, EventBus bus) {
        setBackground(Util.background);
        setLayout(new BorderLayout());
        this.manager = manager;
        this.fileName = manager.getFileName();

        filterBar = new FilterButtonBar(manager.getFilerOptions(), bus);

        initPanel(bus);
        initEventBusSubscribers(bus);
        activateKeybinds();
    }
    private void initEventBusSubscribers(EventBus bus) {
        bus.subscribe(AddListEvent.class, e -> {
            addListEvent(e.getId());
            nextPageMain(bus);
        });
        bus.subscribe(HideModEvent.class, e -> {
            manager.hideModEvent(e.getId());
            nextPageMain(bus);
        });
        bus.subscribe(FavouriteModEvent.class, e -> {
            manager.favouriteModEvent(e.getId());
            nextPageMain(bus);
        });
        bus.subscribe(SelectFilterEvent.class, e -> selectFilterEvent(e.getFilter(), bus));
    }

    private void initPanel(EventBus bus) {
        initTopPanel(bus);
        initBottomPanel(bus);
        initMainPanel(bus);
    }

    private void initTopPanel(EventBus bus) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Util.background);

        JPanel toptopPanel = new JPanel();
        toptopPanel.setBackground(Util.background);
        toptopPanel.setLayout(new BoxLayout(toptopPanel, BoxLayout.X_AXIS));

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Util.background);

        ReturnButton returnButton = new ReturnButton("← Return", bus);

        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchChanged(); }
            public void removeUpdate(DocumentEvent e) { searchChanged(); }
            public void changedUpdate(DocumentEvent e) { searchChanged(); }

            private void searchChanged() {
                nextPageMain(bus);
            }
        });

        filterPanel.add(filterBar);
        toptopPanel.add(Box.createHorizontalStrut(50));
        toptopPanel.add(searchBar);
        toptopPanel.add(Box.createHorizontalStrut(50));
        toptopPanel.add(returnButton);
        toptopPanel.add(Box.createHorizontalStrut(20));

        topPanel.add(new JPanel() {{
            setBackground(Util.background);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(Box.createVerticalStrut(10));
            add(toptopPanel);
            add(Box.createVerticalStrut(20));
        }}, BorderLayout.NORTH);

        topPanel.add(filterPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
    }

    private void initBottomPanel(EventBus bus) {
        bottom = new JPanel();
        bottom.setBackground(Util.background);

        NextButton nextButton = new NextButton(">");
        nextButton.addActionListener(e -> {
            manager.incrementModIndex(7);
            nextPageMain(bus);
        });

        NextButton prevButton = new NextButton("<");
        prevButton.addActionListener(e -> {
            manager.decrementModIndex(7);
            nextPageMain(bus);
        });

        bottom.add(prevButton);
        bottom.add(nextButton);
        add(bottom, BorderLayout.SOUTH);
    }

    private void initMainPanel(EventBus bus) {
        if (mainPanel != null) { remove(mainPanel); }
        mainPanel = new JPanel();
        mainPanel.setBackground(Util.background);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Mod mod : manager.getModList()) {
            ModPanel panel = new ModPanel(mod, bus);
            mainPanel.add(panel, BorderLayout.CENTER);
        }

        add(mainPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Event Handling
    private void nextPageMain(EventBus bus) {
        String currentSearch = searchBar.getText().trim();
        String currentFilter = (filterBar != null && filterBar.getSelected() != null) ? filterBar.getSelected() : "All";
        manager.updateModListForFilterAndSearch(currentSearch, currentFilter);
        initMainPanel(bus);
    }
    private void selectFilterEvent(String filterName, EventBus bus) {
        if (filterName.equals("+")) {
            String newListName = JOptionPane.showInputDialog(SwingUtilities.getWindowAncestor(this), "Enter new list name:");

            if (newListName != null && !newListName.isBlank()) {
                String safeName = newListName.trim().toLowerCase().replaceAll("[^a-z0-9_\\-]", "_");
                String path = "data/" + safeName;
                if (!manager.getManagers().containsKey(newListName)) {
                    IdFileManager newManager = new IdFileManager(path);
                    manager.getManagers().put(newListName, newManager);
                    filterBar.addOption(newListName, bus);
                    manager.filterList.addString(" "+safeName);
                    manager.setCurrentManager(newManager);
                    filterBar.setSelected(newListName);
                }
            } else {
                filterBar.setSelected(manager.getPreviousFilter());
            }
            return;
        }

        manager.selectFilter(filterName);
        nextPageMain(bus);
    }

    private void addListEvent(int id) {
        List<String> listNames = new ArrayList<>(manager.getManagers().keySet());
        if (listNames.isEmpty()) return;

        String selected = (String) JOptionPane.showInputDialog(this,
                "Select a list to add this mod to:", "Add Mod to List", JOptionPane.PLAIN_MESSAGE,
                null, listNames.toArray(), listNames.get(0) );
        if (selected != null && !selected.isBlank()) {
            manager.addModToList(id, selected);
        }
    }
    // Util Methods
    private void activateKeybinds() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    SwingUtilities.invokeLater(() -> searchBar.requestFocusInWindow());
                } else if (e.getKeyCode() == KeyEvent.VK_L) {
                }
                return false;
            }
        });
    }


}
