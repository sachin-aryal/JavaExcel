package com.simple.excel.implementation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Author: SACHIN
 * Date: 4/8/2016.
 */
public class DatabaseSaver extends AbstractDatabaseOperator {

    private ExcelOperator excelOperator;
    private JSONObject finalData;
    JSONArray []eachCol;

    public DatabaseSaver() {

        excelOperator = AbstractExcelOperator.excelOperator;
        if (AbstractExcelOperator.excelField.isMerged()) {
            finalData = excelOperator.getFinalData();
        } else {
            finalData = (JSONObject) excelOperator.getFinalData().get("data");
        }
        DatabaseConfig config = new DatabaseConfig();
        config.createConnection(databaseField);
    }

    @Override
    public void saveData() {

        StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + databaseField.getTableName() + "(");

        ArrayList<String> columnsSet = new ArrayList<>();
        ArrayList<String> dataKeys = new ArrayList<>();
        columnsSet.addAll(databaseField.getDatabaseColumnMap().keySet());
        dataKeys.addAll(databaseField.getDatabaseColumnMap().values());
        IntStream.range(0, columnsSet.size())
                .forEach(idx -> {
                    if (idx == 0) {
                        queryBuilder.append(columnsSet.get(idx));
                    } else {
                        queryBuilder.append("," + columnsSet.get(idx));
                    }
                });
        queryBuilder.append(") VALUES(");

        for (int i = 0; i < columnsSet.size(); i++) {
            if (i == 0) {
                queryBuilder.append("?");
            } else {
                queryBuilder.append(",?");
            }
        }
        queryBuilder.append(")");
        buildData(columnsSet,dataKeys);

        try {
            PreparedStatement preparedStatement = DatabaseConfig.connection.prepareStatement(queryBuilder.toString());
             int maxSize = maxSize();
            for(int i =0;i<maxSize;i++){
                int j=0,k=1;
                for(String col:columnsSet){
                   String type = databaseField.getDatabaseColumnType().get(col);
                    String value = String.valueOf(eachCol[j++].get(i));
                    if(type.equals("INTEGER")){
                        try{
                            preparedStatement.setInt(k,Integer.parseInt(value));
                        }catch (Exception er){
                            preparedStatement.setInt(k, 0);
                        }
                    }else if(type.equals("DOUBLE")){
                        try{
                            preparedStatement.setDouble(k,Double.parseDouble(value));
                        }catch (Exception er){
                            preparedStatement.setDouble(k, 0);
                        }
                    }else{
                        try {
                            preparedStatement.setString(k, value);
                        }catch (Exception er){
                            preparedStatement.setString(k, "-");
                        }
                    }
                    k++;
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                System.out.println("Connection Closed");
                DatabaseConfig.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void buildData(ArrayList<String> columnsSet,ArrayList<String> dataKeys){
        eachCol = new JSONArray[columnsSet.size()];
        int i=0;
        int maxSize = maxSize();
        for(String col:dataKeys){
            eachCol[i] = new JSONArray();
            for(Object innerKey:finalData.keySet()){
                if(col.contains(innerKey.toString())){
                    JSONObject innerObject = (JSONObject) finalData.get(innerKey);
                    col = col.replace("("+innerKey.toString()+")","");
                    JSONArray colVal = (JSONArray) innerObject.get(col);
                    for(int ind=0;ind<maxSize;ind++){
                        try{
                            eachCol[i].add(colVal.get(ind));
                        }catch (Exception er){
                            eachCol[i].add("-");
                        }
                    }
                    break;
                }
            }
            i++;
        }

    }


    public int maxSize(){
        int maxSize=0;
        for (Object key:finalData.keySet()) {
            JSONObject fileJson = (JSONObject) finalData.get(key);
            for(Object cellKey:fileJson.keySet()){
                JSONArray cellValues = (JSONArray) fileJson.get(cellKey);
                try{
                    if(maxSize<cellValues.size()){
                        maxSize=cellValues.size();
                    }
                }catch (Exception ignored){}
            }
        }
        return maxSize;
    }

}