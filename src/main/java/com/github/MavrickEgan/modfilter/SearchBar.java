package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import java.awt.*;

public class SearchBar extends JTextField {

    public SearchBar(){
        super("");
        setForeground(Util.buttonText);
        setBackground(Util.secondary);
        Dimension size = new Dimension(700,35);
        // setPreferredSize(new Dimension(500,35));
        setMaximumSize(size);
        setMinimumSize(size);
        setHorizontalAlignment(JTextField.CENTER);
        setFont(Util.title);
        setBorder(BorderFactory.createEmptyBorder());
    }

}
