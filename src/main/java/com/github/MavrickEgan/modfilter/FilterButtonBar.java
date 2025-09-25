package com.github.MavrickEgan.modfilter;


import com.github.MavrickEgan.eventbus.EventBus;
import com.github.MavrickEgan.eventbus.event.SelectFilterEvent;
import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterButtonBar extends JPanel {
    private final ButtonGroup group = new ButtonGroup();
    private final Map<String, JToggleButton> buttons = new HashMap<>();
    private String selected;

    public FilterButtonBar(List<String> options, EventBus bus) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setBackground(Util.background);

        for (String option : options) {
            JToggleButton btn = createButton(option, bus);

            if (option.equalsIgnoreCase("All")) {
                btn.setSelected(true);
                selected = option;
            }

            group.add(btn);
            buttons.put(option, btn);
            add(btn);
        }
    }

    private JToggleButton createButton(String option, EventBus bus) {
        JToggleButton btn = new JToggleButton(option);
        btn.setFocusPainted(false);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        Dimension minSize = new Dimension(50, 30);
        btn.setMinimumSize(minSize);
        btn.setPreferredSize(minSize);
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE)); // Allow to grow horizontally if needed

        btn.addActionListener(e -> {
            selected = option;
            bus.post(new SelectFilterEvent(option));
        });

        return btn;
    }

    public void setSelected(String option) {
        JToggleButton btn = buttons.get(option);
        if (btn != null) {
            btn.setSelected(true);
            selected = option;
        }
    }

    public String getSelected() {
        return selected;
    }

    public void addOption(String option, EventBus bus) {
        if (!buttons.containsKey(option)) {
            JToggleButton btn = createButton(option, bus);
            group.add(btn);
            buttons.put(option, btn);
            add(btn);
            revalidate();
            repaint();
        }
    }
}