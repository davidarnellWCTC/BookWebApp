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

//    String driverClass;
//    String url;
//    String userName;
//    String password;
    @Override
    public void openConnection(String driverClass, String url, String userName, String password)
            throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);

        // open the connection
        conn = DriverManager.getConnection(url, userName, password);

    }

    @Override
    public void closeConection() throws SQLException {
        conn.close();
    }

    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {
        String sql = "SELECT * FROM " + tableName;

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<Map<String, Object>> records = new ArrayList<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        // Get the column count for the table
        // the column count starts at "1"
        int colCount = rsmd.getColumnCount();

        // limits colCount to the maxRecords value
        if (colCount > maxRecords) {
            colCount = maxRecords;
        }

        while (rs.next()) {
            // use LinkedHashMap to keep column order
            Map<String, Object> record = new LinkedHashMap<>();
            // from column #1 through the entire count of each column
            for (int i = 1; i <= colCount; i++) {
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

    @Override
    public void updateRecordByKey(String tableName, String key, List<String> colNames, List<Object> colValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createNewRecord(String tableName, List<String> colNames, List<Object> colValues)
            throws SQLException {

        PreparedStatement stmt = buildInsertStatement(tableName, colNames, colValues);

        //String sql = "Insert Into " + tableName + " ";
        // this is the basic template for a Sql insert statement
        //INSERT INTO table_name (column1,column2,column3,...)
        //VALUES (value1,value2,value3,...);
        // StringJoiner (separator, prefix, suffix)
        // example (thing_1, thing_2, thing_3)
//        StringJoiner sjColNames = new StringJoiner(", ", " (", ")");
//        
//        for(String colName :colNames){
//            sjColNames.add(colName);
//        }
//        
//        StringJoiner sjColValues = new StringJoiner(", ", " (", ")");
//        //need to add all the values from the colValues
//        // will be 1 shorter than the colNames since the id is generated
//        int i = 1;
//        for(Object colValue : colValues){
//            
//            // tried a few different ways to use the setObject(i, Object) to set
//            // the Object into the Colum value. Could not figure out how 
//            // tried a few different ways including doing it here and in the
//            // prepared statement method.
//            // adding a new ? between each "" in the String Joiner
//            //sjColValues.add("?");
//            
//            //Setting the value to the ? 
//            //stmt.setObject(i, colValue);
//            
//            // this will add the column values to the StringJoiner statement
//            // only works whith String objects. Trying to figure out how to get it
//            // to work with any object but cannot fiture out how to keep it all in the
//            // ("object1", "object2", "etc.") format
//            //sjColValues.add(colValue.toString());
//            
//            
//        }
//        
//        // no idea what to do from here since there's a different number of column
//        // names and values. There are three column names from the Author record
//        // but only two values since the author id is generated by the database
//        
////        sql += sjColNames + " " + sjColValues;
//        
//        // create statement with the sql String created
//        // the PreparedStatement creates a secure insert statement
//        
//        
        String sql = stmt + ";";

        // execute the Sql statement
        stmt.executeQuery(sql);
    }

    @Override
    public void deleteRecordByPrimaryKey(String tableName, String primaryKeyColumnName, Object primaryKeyValue)
            throws SQLException {

        // uses the PreparedStatement buildDeleteStatement to build the statement
        // to be used in the query
        PreparedStatement stmt = buildDeleteStatement(tableName, primaryKeyColumnName, primaryKeyValue);

        // executes the query
        // there is no return value
        stmt.executeQuery();
    }

    private PreparedStatement buildDeleteStatement(String tableName,
            String primaryKeyColumnName, Object primaryKeyValue) throws SQLException {

        String sql = "Delete From " + tableName + " where " + primaryKeyColumnName + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setObject(1, primaryKeyValue);

        return stmt;
    }

    /**
     *
     * @param tableName
     * @param primaryKeyFieldName - has the name of the primaryKey but not the
     * value
     * @return
     * @throws SQLException
     */
    private PreparedStatement buildInsertStatement(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {

        // Initially tried to add all the values into the column names and 
        // column values here. Could not get that to work.
        String sql = "Insert Into " + tableName + " ? ";
//        PreparedStatement stmt = conn.prepareStatement(sql);

        StringJoiner sjColNames = new StringJoiner(", ", " (", ")");
//        
        for (String colName : colNames) {
            sjColNames.add(colName);
        }

        //stmt.setObject(1, sjColNames);
//        
        StringJoiner sjColValues = new StringJoiner(", ", " (", ")");
//        //need to add all the values from the colValues
//        // will be 1 shorter than the colNames since the id is generated
//        int i = 1;
        for (Object colValue : colValues) {
            sql += " ? ";
        }

//        sql += ";";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setObject(1, sjColNames);

        int i = 2;
        for (Object colValue : colValues) {
            stmt.setObject(i, colValue);
        }

        return stmt;
    }

    private PreparedStatement buildUpdateStatement(String tableName, String primaryKeyColumnName,
            String primaryKeyValue, List<String> colNames, List<Object> colValues)
            throws SQLException {

//          UPDATE table_name
//          SET column1=value1,column2=value2,...
//          WHERE some_column=some_value;
        
            String sql = "UPDATE " + tableName + " SET ";
            
            for (String colName : colNames) {
            // set the value for each column name and column value
            sql +="?=?";
        }
            // keeping the primary key value a string until I can figure out how 
            // to make it an object
            sql += ", WHERE " + primaryKeyColumnName + "=" + primaryKeyValue; // addin thhe "," at the end
            
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            int i = 1;
            for (String colName : colNames) {
            // set the value for each column name and column value
            stmt.setObject(i, colName);
            i = i + 2;
        }
            i = 2;
            
            for (Object colValue : colValues) {
            stmt.setObject(i, colValue);
            i += 2;
        }
            
            return stmt;
    }

    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book",
                "root", "admin"); //?useSSL=false after 3306/book in URL if SSL required (secure socket layer)

        List<Map<String, Object>> records = db.findAllRecords("author", 500);
        System.out.println(records);
        db.closeConection();
    }

}
