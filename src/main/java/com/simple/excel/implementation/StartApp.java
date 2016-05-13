package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Author: SACHIN
 * Date: 3/19/2016.
 */
public class StartApp extends JFrame {

    private ExcelField excelp;
    JPanel panel;
    JButton submitButton;
    JTextField columnNumbers;
    JLabel totalFiles;
    JPanel mainPanel;
    public StartApp(){
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();

        new MenuBar().setMenuBar(StartApp.this);
        this.setTitle("Excel Builder");
        JButton fileSelector = new JButton("Choose File");
        setLayout(new BorderLayout());
        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Upload Files"));
        panel.setPreferredSize(new Dimension(400,200));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.insets = new Insets(0,0,0,0);
        panel.add(new JLabel("Select File"),constraints);
        constraints.gridx=1;
        constraints.gridy=0;
        panel.add(fileSelector,constraints);
        constraints.gridx=2;
        constraints.gridy=0;
        constraints.insets = new Insets(0,10,0,0);
        totalFiles = new JLabel("");
        panel.add(totalFiles,constraints);
        constraints.gridx=0;
        constraints.gridy=1;
        constraints.insets = new Insets(40,0,0,20);
        JLabel columnMap = new JLabel("Total Column");
        panel.add(columnMap,constraints);
        constraints.gridx=1;
        constraints.gridy=1;
        constraints.ipadx=60;
        constraints.ipady=5;
        constraints.insets = new Insets(40,0,0,0);
        columnNumbers = new JTextField();
        constraints.ipadx=90;
        panel.add(columnNumbers,constraints);
        constraints.gridx=2;
        constraints.gridy=1;
        constraints.ipadx=20;
        constraints.insets = new Insets(40,10,0,0);
        submitButton = new JButton("Submit");
        submitButton.setVisible(false);
        panel.add(submitButton,constraints);
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        mainPanel.add(panel,mainConstraints);
        add(mainPanel);
        fileSelector.addActionListener(e -> chooseFiles());
        submitButton.addActionListener(e -> moveToMapper());

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void chooseFiles(){
        JPanel mainPanel = new JPanel();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel File","xlsx"));
        fileChooser.setMultiSelectionEnabled(true);
        int returnVal = fileChooser.showOpenDialog(mainPanel);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            File[] files =  fileChooser.getSelectedFiles();

            for(File file:files){
                if(!file.getName().contains(".xlsx")){
                    dialogueBox("File format not supported. Only works for .xlsx format",JOptionPane.ERROR_MESSAGE,"Format Exception");
                    return;
                }
            }
            totalFiles.setText(files.length+" files uploaded.");
            excelp = new ExcelField();
            excelp.setFile(files);
            submitButton.setVisible(true);

        }else if(returnVal==JFileChooser.ERROR_OPTION){
            dialogueBox("Error while uploading file.",JOptionPane.ERROR_MESSAGE,"Error");
            return;
        }

    }

    public void moveToMapper(){
        int totalColumns = Integer.parseInt(columnNumbers.getText());
        excelp.setTotalColumn(totalColumns);
        AbstractExcelOperator.setExcelField(excelp);
        new ExcelMapper();
        this.dispose();
    }

    public void dialogueBox(String message,int messageType,String title){
        JOptionPane.showMessageDialog (null, message, title, messageType);
    }


}
