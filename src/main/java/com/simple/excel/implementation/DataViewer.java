package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;

/**
 * Author: SACHIN
 * Date: 3/30/2016.
 */
public class DataViewer extends AbstractExcelOperator{

    private ExcelField excelField;
    private ExcelOperator excelOperator;
    private JTable table;
    private Object [][] cells;
    private String columns[];
    private JFrame frame;
    private JTextField columnNumber[];
    private JTextField columnsToBeMerged;
    private JPanel mainPanel;


    public DataViewer(ExcelField excelField, ExcelOperator excelOperator){
        this.excelField=excelField;
        this.excelOperator=excelOperator;
        this.frame = new JFrame();
        new MenuBar().setMenuBar(frame);
        showProcessedData();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void showProcessedData(){
        int totalColumns = excelField.getTotalColumn()*excelField.getFile().length;
        columns= new String[totalColumns];
        int j=0;
        for(int i =0;i<excelField.getFile().length;i++){
            for(String eachColumns:excelField.getColumnMaps().keySet()){
                columns[j]=eachColumns;
                j++;
            }
        }
        cells = new Object[maxSize()][totalColumns];
        processedData();
       doDesign();
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

        JPanel mergerPanel = new JPanel(new GridBagLayout());
        mergerPanel.setBorder(BorderFactory.createTitledBorder("Merge Columns"));
        mergerPanel.setPreferredSize(new Dimension(400,200));
        GridBagConstraints mergePanelConstraints = new GridBagConstraints();

        JLabel numColLabel = new JLabel("Number of Column");
        mergePanelConstraints.gridx=0;
        mergePanelConstraints.gridy=0;
        mergerPanel.add(numColLabel,mergePanelConstraints);

        columnsToBeMerged = new JTextField();
        mergePanelConstraints.gridx=1;
        mergePanelConstraints.gridy=0;
        mergePanelConstraints.ipadx=135;
        mergePanelConstraints.ipady=10;
        mergePanelConstraints.insets = new Insets(0,8,0,0);
        mergerPanel.add(columnsToBeMerged,mergePanelConstraints);

        JButton addMergeColumn = new JButton("Add Column");
        mergePanelConstraints.gridx=1;
        mergePanelConstraints.gridy=1;
        mergePanelConstraints.ipadx=40;
        mergePanelConstraints.ipady=10;
        mergePanelConstraints.insets = new Insets(30,8,0,0);
        mergerPanel.add(addMergeColumn,mergePanelConstraints);

        JButton exampleButton = new JButton("Example");
        mergePanelConstraints.gridx=2;
        mergePanelConstraints.gridy=1;
        mergePanelConstraints.ipadx=40;
        mergePanelConstraints.ipady=10;
        mergePanelConstraints.insets = new Insets(30,8,0,0);
        mergerPanel.add(exampleButton,mergePanelConstraints);

        exampleButton.addActionListener(new ExampleButtonListener());


        JPanel processDataPanel = new JPanel();
        JButton processToDatabaseButton = new JButton("Save");
        processDataPanel.add(processToDatabaseButton);

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


        addMergeColumn.addActionListener(e -> questionDialogue());

        mainPanel.setBorder(new LineBorder(new Color(0,0,0),2));
        frame.add(mainPanel,BorderLayout.CENTER);
    }

    public void moveToColumnMerger(){
        String columnsToBeMergedText = columnsToBeMerged.getText();
        new ExcelColumnMerger(excelOperator,excelField,columnsToBeMergedText).mergeColumns();
        new MergeDataView(excelField,excelOperator).showProcessedData();
        frame.dispose();
    }

    public void processedData(){

        JSONObject data = excelOperator.getFinalData();
        JSONObject finalData = (JSONObject) data.get("data");
        int row=0,col=0;
        for (File file : excelField.getFile()) {
            for (String cellName : excelField.getColumnMaps().keySet()) {
                row=0;
                JSONObject fileJson = (JSONObject) finalData.get(file.getName());
                JSONArray allCellData = (JSONArray) fileJson.get(cellName);
                try {
                    Iterator<String> iterator = allCellData.iterator();
                    while (iterator.hasNext()) {
                       cells[row][col]=iterator.next();
                        row++;
                    }
                }catch (NullPointerException ex){
                    cells[row][col]="";
                }
                col++;
            }
        }

    }

    public void questionDialogue(){
        int result = JOptionPane.showConfirmDialog(frame,"Are you Sure?","Merge Column",JOptionPane.YES_NO_OPTION);
        if(result==0){
            moveToColumnMerger();
        }else{
            return;
        }
    }

    public int maxSize(){
        int maxSize=0;
        JSONObject data = excelOperator.getFinalData();
        JSONObject finalData = (JSONObject) data.get("data");
        for (File file : excelField.getFile()) {
            for (String cellName : excelField.getColumnMaps().keySet()) {
                JSONObject fileJson = (JSONObject) finalData.get(file.getName());
                JSONArray allCellData = (JSONArray) fileJson.get(cellName);
                if(allCellData!=null){
                    if(allCellData.size()>maxSize){
                        maxSize=allCellData.size();
                    }
                }
            }
        }
        return maxSize;
    }

    class ExampleButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,"For One Type Of Column:0-3-4\nFor Multiple Type of Columns: 0-3-4,1-2-5",
                    "Merge Columns",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

}
