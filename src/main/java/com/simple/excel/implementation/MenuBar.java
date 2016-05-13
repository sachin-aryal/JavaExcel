package com.simple.excel.implementation;

import javax.swing.*;
import java.awt.*;

/**
 * Author: SACHIN
 * Date: 3/29/2016.
 */
public class MenuBar {
    JMenuBar menubar;

    public void setMenuBar(JFrame frame){
        menubar = new JMenuBar();
        JMenu homeMenu = new JMenu("Menu");
        menubar.add(homeMenu);
        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem exitItem = new JMenuItem("Exit");
        homeMenu.add(homeItem);
        homeMenu.add(exitItem);
        homeItem.addActionListener(e -> {
            frame.dispose();
            new StartApp();
        });
        exitItem.addActionListener(e-> System.exit(0));
        menubar.setPreferredSize(new Dimension(frame.getWidth(),50));
        menubar.setBackground(new Color(121, 131, 255));
        frame.setJMenuBar(menubar);
    }
}
