package com.simple.excel.implementation;

import javax.swing.*;
import java.awt.*;

/**
 * Author: SACHIN
 * Date: 3/30/2016.
 */
public class BackGroundProcessor{

    private String threadName;
    private JFrame frame;

    public BackGroundProcessor(String threadName, JFrame frame){
        this.threadName=threadName;
        this.frame=frame;
    }

    public void processInBack(){

        JDialog dlgProgress = new JDialog(frame, "Processing", true);
        JLabel lblStatus = new JLabel("Please wait");

        JProgressBar pbProgress = new JProgressBar();
        pbProgress.setIndeterminate(true);
        pbProgress.setPreferredSize(new Dimension(300,20));

        dlgProgress.add(BorderLayout.PAGE_START, lblStatus);
        dlgProgress.add(BorderLayout.CENTER, pbProgress);
        dlgProgress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlgProgress.setSize(300, 70);
        dlgProgress.setLocationRelativeTo(frame);

        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception{
                if(threadName.equalsIgnoreCase("progressBar")){
                    ExcelOperator excelOperator = ExcelFactory.getObjectInstance("ExcelOperation");
                    ExcelOperator excelReader = ExcelFactory.getObjectInstance("ExcelReader");
                    if (excelReader != null) {
                        excelReader.processFile();
                        if (excelOperator != null) {
                            excelOperator.setFinalData(excelReader.getFinalData());
                        }
                    }
                    ExcelOperator excelOperatorB = ExcelFactory.getObjectInstance("ExcelBuilder");
                    AbstractExcelOperator.setExcelOperator(excelOperator);
                    if(excelOperatorB!=null){
                        excelOperatorB.generateExcel(false);
                    }
                }else if(threadName.equals("databaseSaver")){
                    DatabaseOperator databaseOperator = DatabaseFactory.getObjectInstance("DatabaseSaver");
                    if (databaseOperator != null) {
                        databaseOperator.saveData();
                    }
                }
                return null;
            }
            @Override
            protected void done(){
                dlgProgress.dispose();
            }
        };
        mySwingWorker.execute();
        dlgProgress.setVisible(true);
    }

    public void processInBack(String columnsToBeMergedText){

        JDialog dlgProgress = new JDialog(frame, "Processing", true);
        JLabel lblStatus = new JLabel("Please wait");

        JProgressBar pbProgress = new JProgressBar();
        pbProgress.setIndeterminate(true);
        pbProgress.setPreferredSize(new Dimension(300,20));

        dlgProgress.add(BorderLayout.PAGE_START, lblStatus);
        dlgProgress.add(BorderLayout.CENTER, pbProgress);
        dlgProgress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlgProgress.setSize(300, 70);
        dlgProgress.setLocationRelativeTo(frame);

        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception{
                new ExcelColumnMerger(columnsToBeMergedText).mergeColumns();
                return null;
            }
            @Override
            protected void done(){
                dlgProgress.dispose();
            }
        };
        mySwingWorker.execute();
        dlgProgress.setVisible(true);
    }







}
