package com.simple.excel.implementation;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public class ExcelMapper extends AbstractExcelOperator{

    JPanel panel;
    JTextField columnName[];
    JComboBox<String> columnType[];
    JLabel columnNameLabel;
    JButton mapButton;
    JButton processFileButton;
    JPanel mainPanel;
    JFrame frame;

    public ExcelMapper(){
        frame = new JFrame();
        frame.setTitle("Excel Mapper");
        new MenuBar().setMenuBar(frame);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();


        panel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        columnName = new JTextField[excelField.getTotalColumn()];
        columnType = new JComboBox[excelField.getTotalColumn()];
        int j=0,k=0;
        for(int i=0;i<excelField.getTotalColumn();i++){
            cons.gridx=j;
            cons.gridy=k;
            cons.insets=new Insets(10,0,0,10);
            cons.ipadx=0;
            cons.ipady=0;
            columnNameLabel = new JLabel("Column "+(i+1));
            panel.add(columnNameLabel,cons);
            cons.gridx=j+1;
            cons.gridy=k;
            cons.ipadx=150;
            cons.ipady=10;
            cons.insets=new Insets(20,0,0,50);
            columnName[i] = new JTextField();
            panel.add(columnName[i],cons);
            cons.gridx=j+2;
            cons.gridy=k;
            cons.ipadx=100;
            cons.insets=new Insets(20,0,0,0);
            columnType[i] = new JComboBox<>();
            columnType[i].addItem("INTEGER");
            columnType[i].addItem("STRING");
            columnType[i].addItem("DOUBLE");
            panel.add(columnType[i],cons);
            k++;
        }
        cons.gridx=1;
        cons.gridy=k;
        cons.ipadx=50;
        cons.insets=new Insets(20,0,0,50);
        mapButton = new JButton("Set Mapping");
        panel.add(mapButton,cons);
        cons.gridx=2;
        cons.gridy=k;
        cons.ipadx=70;
        cons.insets=new Insets(20,0,0,0);
        processFileButton = new JButton("Process File");
        panel.add(processFileButton,cons);
        processFileButton.setEnabled(false);
        processFileButton.addActionListener(e -> new StartBuilder().startProcess(frame));
        mapButton.addActionListener(e-> {
            setMapping();
            processFileButton.setEnabled(true);
        });

        panel.setBorder(BorderFactory.createTitledBorder("Columns List"));
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        mainPanel.add(panel,mainConstraints);

        JScrollPane jsp = new JScrollPane(mainPanel);
        frame.add(jsp);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void setMapping(){

        Map<String,String> columnMap = new LinkedHashMap<>();
        for(int i=0;i<columnName.length;i++){
            columnMap.put(columnName[i].getText(),columnType[i].getSelectedItem().toString());
        }
        excelField.setColumnMaps(columnMap);
    }




}
