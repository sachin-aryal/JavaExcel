package com.simple.excel.implementation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StartApp();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.setJMenuBar(menubar);
    }
}
