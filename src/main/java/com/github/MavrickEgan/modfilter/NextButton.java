package com.github.MavrickEgan.modfilter;

import com.github.MavrickEgan.util.Util;

import javax.swing.*;
import java.awt.*;

public class NextButton extends JButton {
    public NextButton(String name) {
        super(name);
        setForeground(Util.buttonText);
        setBackground(Util.button);
        setFont(Util.buttonBig);
        setPreferredSize(new Dimension(200,50));
        setAlignmentX(Component.RIGHT_ALIGNMENT);
    }
}
