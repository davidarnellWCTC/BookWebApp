/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author David Arnell
 */
public class MySqlDbStrategy implements DbStrategy {
    
    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url, String userName, String password)
            throws ClassNotFoundException, SQLException {
        
        Class.forName (driverClass);
        
        // open the connection
        conn = DriverManager.getConnection(url, userName, password);

    }
    
    @Override
    public void closeConection() throws SQLException{
        conn.close();
    }
    
    @Override
    public List<Map<String, Object>> findAllRecords (String tableName, int maxRecords) throws SQLException{
        String sql = "SELECT * FROM " + tableName;
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(sql);
        
        List<Map<String, Object>> records = new ArrayList<>();
        
        ResultSetMetaData rsmd = rs.getMetaData();
        // Get the column count for the table
        // the column count starts at "1"
        int colCount = rsmd.getColumnCount();
        
        // limits colCount to the maxRecords value
        if (colCount > maxRecords){
            colCount = maxRecords;
        }
                
        while(rs.next()){
            // use LinkedHashMap to keep column order
            Map<String,Object> record = new LinkedHashMap<>();
            // from column #1 through the entire count of each column
            for(int i = 1; i <= colCount; i++){
                // retrieves the column name to be used as the Key for the LinkedHashMap
                String colName = rsmd.getColumnName(i);
                //get the data from the results set from the column name
                Object colData = rs.getObject(colName);
                // adds the colData as an Object with the colName as the key for the LinkedHashMap
                record.put(colName, colData);
            }
            records.add(record);
        }
        return records;
    }
    
    

//    @Override
//    public Map<String, Object> findRecordByKey(String tableName, String key)  throws SQLException{
//String sql = "SELECT * FROM " + tableName;
//        
//        Statement stmt = conn.createStatement();
//        
//        ResultSet rs = stmt.executeQuery(sql);
//        
//        List<Map<String, Object>> records = new ArrayList<>();
//        
//        ResultSetMetaData rsmd = rs.getMetaData();
//        // Get the column count for the table
//        // the column count starts at "1"
//        int colCount = rsmd.getColumnCount();
//        
//                
//        while(rs.next()){
//            // use LinkedHashMap to keep column order
//            Map<String,Object> record = new LinkedHashMap<>();
//            // from column #1 through the entire count of each column
//            for(int i = 1; i <= colCount; i++){
//                // retrieves the column name to be used as the Key for the LinkedHashMap
//                String colName = rsmd.getColumnName(i);
//                //get the data from the results set from the column name
//                Object colData = rs.getObject(colName);
//                // adds the colData as an Object with the colName as the key for the LinkedHashMap
//                record.put(colName, colData);
//            }
//            records.add(record);
//        }
//        return records;
//    }

    @Override
    public void updateRecordByKey(String tableName, String key, List<String> colNames, List<Object> colValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createNewRecord(String tableName, List<String> colNames, List<Object> colValues)  
            throws SQLException {
        String sql = "Insert Into " + tableName + " ";
        
        // this is the basic template for a Sql insert statement
        //INSERT INTO table_name (column1,column2,column3,...)
        //VALUES (value1,value2,value3,...);
        
        // StringJoiner (separator, prefix, suffix)
        // example (thing_1, thing_2, thing_3)
        StringJoiner sjColNames = new StringJoiner(", ", " (", ")");
        
        for(String colName :colNames){
            sjColNames.add(colName);
        }
        
        StringJoiner sjColValues = new StringJoiner(", ", " (", ")");
        //need to add all the values from the colValues
        // will be 1 shorter than the colNames since the id is generated
        for(Object colValue : colValues){
            // this will add the column values to the StringJoiner statement
            sjColValues.add(colValue.toString());
        }
        
        // no idea what to do from here since there's a different number of column
        // names and values. There are three column names from the Author record
        // but only two values since the author id is generated by the database
        
        sql += sjColNames + " " + sjColValues;
        
        // create statement with the sql String created
        Statement stmt = conn.createStatement();
        
        // execute the Sql statement
        stmt.executeQuery(sql);
    }

    @Override
    public void deleteRecordByPrimaryKey(String tableName, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private PreparedStatement buildDeleteStatement(String tableName, 
            String primaryKeyFieldName, Object primaryKeyValue) throws SQLException{
        
        String sql = "Delete From " + tableName + " ";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setObject(1, primaryKeyValue);
        
        return stmt;
    }
    
    
    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", 
                "root", "admin"); //?useSSL=false after 3306/book in URL if SSL required (secure socket layer)
        
        List<Map<String, Object>> records = db.findAllRecords("author",500);
        System.out.println(records);
        db.closeConection();
    }

}
