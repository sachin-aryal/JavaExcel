package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Author: SACHIN
 * Date: 4/5/2016.
 */
public class MergeDataView extends AbstractExcelOperator{

    private ExcelField excelField;
    private ExcelOperator excelOperator;
    private JTable table;
    private Object [][] cells;
    private String columns[];
    private JFrame frame;
    private JPanel mainPanel;
    private JSONObject mergedData;
    JPanel mergerPanel;
    GridBagConstraints mergePanelConstraints;


    public MergeDataView(ExcelField excelField,ExcelOperator excelOperator){
        frame = new JFrame();
        this.excelField = excelField;
        this.excelOperator = excelOperator;
        new MenuBar().setMenuBar(frame);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        System.out.println(excelOperator.getFinalData());
        this.mergedData=excelOperator.getFinalData();
    }

    @Override
    public void showProcessedData(){
        addColumns();
        cells = new Object[maxSize()][columns.length];
        doDesign();
        processedData();
    }

    public void doDesign(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(1600,1600));
        GridBagConstraints mainConstraints = new GridBagConstraints();

        JPanel tablePanel = new JPanel();
        table = new JTable(cells,columns);
        table.setDragEnabled(false);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(800,500));
        tablePanel.add(tableScroll);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data View"));

        mergerPanel = new JPanel(new GridBagLayout());
        mergerPanel.setBorder(BorderFactory.createTitledBorder("Merged Columns"));
        mergerPanel.setPreferredSize(new Dimension(400,200));
        mergePanelConstraints = new GridBagConstraints();


        JPanel processDataPanel = new JPanel();
        JButton processToDatabaseButton = new JButton("Save");
        processDataPanel.add(processToDatabaseButton);
        processToDatabaseButton.addActionListener(e -> save());

        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
//        mainConstraints.fill=GridBagConstraints.HORIZONTAL;
        mergePanelConstraints.insets = new Insets(0,0,0,100);
        mainConstraints.anchor=GridBagConstraints.WEST;
        mainPanel.add(tablePanel,mainConstraints);

        mergePanelConstraints.weightx=0;
        mainConstraints.fill=GridBagConstraints.NONE;
        mainConstraints.gridx=2;
        mainConstraints.gridy=0;
        mainConstraints.anchor=GridBagConstraints.PAGE_START;
        mergePanelConstraints.insets = new Insets(0,100,0,0);
        mainPanel.add(Box.createHorizontalStrut(70));
        mainPanel.add(mergerPanel,mainConstraints);


        mainConstraints.gridx=0;
        mainConstraints.gridy=1;
        mainConstraints.anchor=GridBagConstraints.PAGE_END;
        mainPanel.add(processDataPanel,mainConstraints);


        mainPanel.setBorder(new LineBorder(new Color(0,0,0),2));
        frame.add(mainPanel,BorderLayout.CENTER);
    }

    public void save(){
        ExcelOperator excelOperatorB = ExcelFactory.getObjectInstance("ExcelBuilder");
        if(excelOperatorB!=null){
            excelOperatorB.generateExcel(excelField,excelOperator,true);
        }
    }

    public void processedData(){

        int row=0,col=0;

        for(Object key:mergedData.keySet()){
            JSONObject innerJson = (JSONObject) mergedData.get(key);
            for(Object cellKey:innerJson.keySet()){
                row=0;
                JSONArray cellValues = (JSONArray) innerJson.get(cellKey);
                for(Object val:cellValues){
                    cells[row][col]=val;
                    row++;
                }
                col++;
            }
        }
        int y=0;
        for(String mergedCell:excelField.getMergedColumns()){
            JLabel label = new JLabel((y+1)+". "+mergedCell.substring(0,(mergedCell.length()-1)));
            mergePanelConstraints.gridx=0;
            mergePanelConstraints.gridy=y;
            mergePanelConstraints.anchor=GridBagConstraints.PAGE_START;
            mergerPanel.add(label,mergePanelConstraints);
            y++;
        }

    }

    public void addColumns(){
        int totalColumns = 0;
        for (Object key:mergedData.keySet()) {
            JSONObject fileJson = (JSONObject) mergedData.get(key);
            totalColumns+=fileJson.keySet().size();
        }
        columns= new String[totalColumns];
        int i=0;
        for (Object key:mergedData.keySet()) {
            JSONObject fileJson = (JSONObject) mergedData.get(key);
            for(Object cellKey : fileJson.keySet()){
               columns [i] = cellKey.toString();
                i++;
            }
        }
    }

    public int maxSize(){
        int maxSize=0;
        for (Object key:mergedData.keySet()) {
            JSONObject fileJson = (JSONObject) mergedData.get(key);
            for(Object cellKey:fileJson.keySet()){
                JSONArray cellValues = (JSONArray) fileJson.get(cellKey);
                try{
                    if(maxSize<cellValues.size()){
                        maxSize=cellValues.size();
                    }
                }catch (Exception er){}
            }
        }
        return maxSize;
    }


}
