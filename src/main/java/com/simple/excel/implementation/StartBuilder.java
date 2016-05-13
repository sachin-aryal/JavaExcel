package com.simple.excel.implementation;

import javax.swing.*;

/**
 * Author: SACHIN
 * Date: 3/30/2016.
 */
public class StartBuilder {

    public void startProcess(JFrame frame){
        new BackGroundProcessor("progressBar",frame).processInBack();
        new DataViewer();
        frame.dispose();
    }
}
