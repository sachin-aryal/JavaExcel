package com.simple.excel.implementation;

import com.simple.pozo.DatabaseField;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Author: SACHIN
 * Date: 4/6/2016.
 */
public class DatabaseMapper extends AbstractDatabaseOperator{

    JPanel panel;
    JTextField columnName[];
    JTextField databaseColumnNames[];
    JComboBox<String>[] columnType;
    JLabel columnNameLabel;
    JButton mapButton;
    JButton processFileButton;
    JPanel mainPanel;
    JFrame frame;
    JTextField hostField;
    JTextField databaseFieldText;
    JTextField userNameText;
    JTextField passwordText;
    JTextField tableNameText;
    private ExcelOperator excelOperator;
    private LinkedList<String> keyList;

    public DatabaseMapper(){
        frame = new JFrame();
        excelOperator = AbstractExcelOperator.getExcelOperator();
        keyList  = new LinkedList<>();
    }

    public void setDatabaseColumnMapping(){
        keyList();
        frame.setTitle("Database Column Mapper");
        new MenuBar().setMenuBar(frame);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        columnName = new JTextField[keyList.size()];
        databaseColumnNames = new JTextField[keyList.size()];
        columnType = new JComboBox[keyList.size()];
        int j=0,k=0;
        for(int i=0;i<keyList.size();i++){
            cons.gridx=j;
            cons.gridy=k;
            cons.insets=new Insets(10,0,0,10);
            cons.ipadx=0;
            cons.ipady=0;
            columnNameLabel = new JLabel("Column "+(i+1));
            panel.add(columnNameLabel,cons);
            cons.gridx=j+1;
            cons.gridy=k;
            cons.insets=new Insets(20,0,0,50);
//            cons.ipadx=150;
//            cons.ipady=10;
            columnName[i] = new JTextField();
            columnName[i].setText(keyList.get(k));
            columnName[i].setEnabled(false);
            columnName[i].setForeground(new Color(255,255,255));
            columnName[i].setBackground(new Color(0,0,0));
            columnName[i].setPreferredSize(new Dimension(150,25));
            panel.add(columnName[i],cons);
            cons.gridx=j+2;
            cons.gridy=k;
            cons.ipadx=150;
            cons.ipady=10;
            cons.insets=new Insets(20,0,0,50);
            databaseColumnNames[i] = new JTextField();
            panel.add(databaseColumnNames[i],cons);
            cons.gridx=j+3;
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
        cons.ipadx=50;
        cons.insets=new Insets(20,0,0,0);
        processFileButton = new JButton("Database Process");
        panel.add(processFileButton,cons);
        processFileButton.setEnabled(false);
        processFileButton.addActionListener(e -> processToDatabase());
        mapButton.addActionListener(e-> {
            setMapping();
            processFileButton.setEnabled(true);
        });


        panel.setBorder(BorderFactory.createTitledBorder("Database Mapping"));
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        mainPanel.add(panel,mainConstraints);

        JScrollPane jsp = new JScrollPane(mainPanel);
        jsp.setPreferredSize(new Dimension(800,0));
        frame.add(jsp);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

//    int keySize(){
//        int keySize = 0;
//        JSONObject object = excelOperator.getFinalData();
//        for(Object key:object.keySet()){
//            JSONObject innerObj = (JSONObject) object.get(key);
//            keySize+=innerObj.keySet().size();
//        }
//        return keySize;
//    }

    public LinkedList<String> keyList(){
        JSONObject object=null;
        if(AbstractExcelOperator.excelField.isMerged()){
            object = excelOperator.getFinalData();
        }else{
            object = (JSONObject) excelOperator.getFinalData().get("data");
        }
        for(Object key:object.keySet()){
            JSONObject innerObj = (JSONObject) object.get(key);
            for(Object cellKeys : innerObj.keySet()){
                keyList.add(String.valueOf(cellKeys)+"("+String.valueOf(key)+")");
            }
        }
        return keyList;
    }

    public void setMapping(){
        DatabaseField databaseField = new DatabaseField();
        AbstractDatabaseOperator.setDatabaseField(databaseField);
        for(int i=0;i<columnName.length;i++){
            databaseField.getDatabaseColumnMap().put(databaseColumnNames[i].getText(),columnName[i].getText());
            databaseField.getDatabaseColumnType().put(databaseColumnNames[i].getText(),columnType[i].getSelectedItem().toString());
        }
    }


    public void processToDatabase(){
        JDialog databaseDialogue = new JDialog(frame,"Database",true);
        JPanel dataPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        JLabel hostLabel = new JLabel("Host");
        cons.gridx=0;
        cons.gridy=0;
        cons.insets=new Insets(10,0,0,10);
        cons.ipadx=0;
        cons.ipady=0;
        dataPanel.add(hostLabel,cons);

        hostField = new JTextField();
        cons.gridx=1;
        cons.gridy=0;
        cons.ipadx=150;
        cons.ipady=10;
        cons.insets=new Insets(20,0,0,50);
        dataPanel.add(hostField,cons);

        JLabel databaseNameLabel = new JLabel("Database Name");
        cons.gridx=0;
        cons.gridy=1;
        cons.insets=new Insets(10,0,0,10);
        cons.ipadx=0;
        cons.ipady=0;
        dataPanel.add(databaseNameLabel,cons);

        databaseFieldText = new JTextField();
        cons.gridx=1;
        cons.gridy=1;
        cons.ipadx=150;
        cons.ipady=10;
        cons.insets=new Insets(20,0,0,50);
        dataPanel.add(databaseFieldText,cons);


        JLabel userNameLabel = new JLabel("Username");
        cons.gridx=0;
        cons.gridy=2;
        cons.insets=new Insets(10,0,0,10);
        cons.ipadx=0;
        cons.ipady=0;
        dataPanel.add(userNameLabel,cons);

        userNameText = new JTextField();
        cons.gridx=1;
        cons.gridy=2;
        cons.ipadx=150;
        cons.ipady=10;
        cons.insets=new Insets(20,0,0,50);
        dataPanel.add(userNameText,cons);



        JLabel passwordLabel = new JLabel("Password");
        cons.gridx=0;
        cons.gridy=3;
        cons.insets=new Insets(10,0,0,10);
        cons.ipadx=0;
        cons.ipady=0;
        dataPanel.add(passwordLabel,cons);


        passwordText = new JTextField();
        cons.gridx=1;
        cons.gridy=3;
        cons.ipadx=150;
        cons.ipady=10;
        cons.insets=new Insets(20,0,0,50);
        dataPanel.add(passwordText,cons);


        JLabel tableNameLabel = new JLabel("Table Name");
        cons.gridx=0;
        cons.gridy=4;
        cons.insets=new Insets(10,0,0,10);
        cons.ipadx=0;
        cons.ipady=0;
        dataPanel.add(tableNameLabel,cons);

        tableNameText = new JTextField();
        cons.gridx=1;
        cons.gridy=4;
        cons.ipadx=150;
        cons.ipady=10;
        cons.insets=new Insets(20,0,0,50);
        dataPanel.add(tableNameText,cons);

        JButton submitButton = new JButton("Save");
        cons.gridx=0;
        cons.gridy=5;
        cons.ipadx=0;
        cons.ipady=0;
        cons.insets=new Insets(10,0,0,10);
        dataPanel.add(submitButton,cons);

        submitButton.addActionListener(listener->moveToBackService());

        databaseDialogue.add(BorderLayout.CENTER,dataPanel);

        databaseDialogue.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        databaseDialogue.setSize(400, 400);
        databaseDialogue.setLocationRelativeTo(frame);

        databaseDialogue.setVisible(true);


    }

    public void moveToBackService(){

        databaseField.setDatabaseName(databaseFieldText.getText());
        databaseField.setHost(hostField.getText());
        databaseField.setUserName(userNameText.getText());
        databaseField.setPassword(passwordText.getText());
        databaseField.setTableName(tableNameText.getText());

        new BackGroundProcessor("databaseSaver",frame).processInBack();
    }






}
