package com.simple.excel.implementation;

import javax.swing.*;
import java.awt.*;

/**
 * Author: SACHIN
 * Date: 3/30/2016.
 */
public class DialogueBuilder{

    private JDialog dialog=null;
    private Thread thread;
    private String threadName;
    private JPanel panel;
    private JFrame frame;

    public DialogueBuilder(String threadName,JFrame frame){
        this.threadName=threadName;
        this.frame=frame;
        panel = new JPanel();
    }

    public void showWaitDialogue(){

        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {

                if(threadName.equalsIgnoreCase("progressBar")){
                    dialog = new JDialog(frame, "Processing", Dialog.ModalityType.APPLICATION_MODAL);
                    JProgressBar progressBar = new JProgressBar();
                    progressBar.setIndeterminate(true);
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(progressBar, BorderLayout.CENTER);
                    panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
                    dialog.add(panel);
                    dialog.pack();
                    dialog.setVisible(true);
                }

                return null;
            }
        };
        mySwingWorker.execute();
    }


}
