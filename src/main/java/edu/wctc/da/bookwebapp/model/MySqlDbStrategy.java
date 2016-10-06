/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.io.Serializable;
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
public class MySqlDbStrategy implements DbStrategy, Serializable {

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

    /**
     *
     * @param tableName
     * @param primaryKeyValue
     * @param primaryKeyColumnName
     * @param colNames
     * @param colValues
     * @throws SQLException
     */
    @Override
    public void updateRecordByKey(String tableName, Object primaryKeyValue,
            String primaryKeyColumnName, List<String> colNames, List<Object> colValues)
            throws SQLException {

        PreparedStatement stmt = buildUpdateStatement(tableName, primaryKeyColumnName,
                colNames);

        // sets the object values for each ? in the prepared statement
        for (int i = 0; i < colValues.size(); i++) {
            stmt.setObject(i + 1, colValues.get(i));
        }

        // sets the value for the pkValue in the last ?
        stmt.setObject(colValues.size() + 1, primaryKeyValue);
        // execute the Sql statement
        stmt.executeUpdate();
    }

    @Override
    public void createNewRecord(String tableName, List<String> colNames, List<Object> colValues)
            throws SQLException {

        PreparedStatement stmt = buildInsertStatement(tableName, colNames);
        for (int i = 0; i < colValues.size(); i++) {
            stmt.setObject(i + 1, colValues.get(i));
        }
        // where clause
        //stmt.setObject(colValues.size() + 1, conn);
        // execute the Sql statement
        stmt.executeUpdate();
    }

    @Override
    public void deleteRecordByPrimaryKey(String tableName, String primaryKeyColumnName, Object primaryKeyValue)
            throws SQLException {

        // uses the PreparedStatement buildDeleteStatement to build the statement
        // to be used in the query
        PreparedStatement stmt = buildDeleteStatement(tableName, primaryKeyColumnName, primaryKeyValue);

        stmt.setObject(1, primaryKeyValue);
        
        // executes the query
        // there is no return value
        stmt.executeUpdate();
    }

    private PreparedStatement buildDeleteStatement(String tableName,
            String primaryKeyColumnName, Object primaryKeyValue) throws SQLException {

        String sql = "Delete From " + tableName + " where " + primaryKeyColumnName + " = ?;";

        PreparedStatement stmt = conn.prepareStatement(sql);

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
    private PreparedStatement buildInsertStatement(String tableName, List<String> colNames) throws SQLException {

        /*
        INSERT INTO table_name (column1,column2,column3,...)
        VALUES (value1,value2,value3,...);        
         */
        String sql = "Insert Into " + tableName;

        StringJoiner sjColNames = new StringJoiner(", ", " (", ") ");
        
        for (String colName : colNames) {
            sjColNames.add(colName);
        }
        
        sql += sjColNames;
       
        StringJoiner sjColValues = new StringJoiner(", ", " (", ")");
        
        System.out.println(sql);
        
        for (String colName : colNames) {
            sjColValues.add("?");
        }
        
        sql += " VALUES " + sjColValues + ";";
        
        System.out.println(sql);
        
        PreparedStatement stmt = conn.prepareStatement(sql);

        return stmt;
    }

    private PreparedStatement buildUpdateStatement(String tableName, String primaryKeyColumnName,
            List<String> colNames)
            throws SQLException {

//          UPDATE table_name
//          SET column1=value1,column2=value2,...
//          WHERE some_column=some_value;
        String sql = "UPDATE " + tableName + " SET ";

        // set a ? for each column and the column name for each value/?
        for (String colName : colNames) {
            // set the value for each column name and column value
            sql += (colName + "=?,");
        }
        // delete the last ,
        sql = sql.substring(0, sql.length() - 1);

        // keeping the primary key value a string until I can figure out how 
        // to make it an object
        sql += " WHERE " + primaryKeyColumnName + "=?"; // addin thhe "," at the end

        // the sql string contains the table_name, columnNames, and the pk column name
        PreparedStatement stmt = conn.prepareStatement(sql);

        return stmt;
    }

//    private PreparedStatement buildUpdateStatement(String tableName, String primaryKeyColumnName,
//            String primaryKeyValue, List<String> colNames, List<Object> colValues){
//        
//    }
    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book",
                "root", "admin"); //?useSSL=false after 3306/book in URL if SSL required (secure socket layer)

        List<Map<String, Object>> records = db.findAllRecords("author", 500);
        System.out.println(records);
        db.closeConection();
    }

}
