/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David Arnell
 */
public interface DbStrategy {

    void closeConection() throws SQLException;
    
    //C.R.U.D
    // Create, Read, Update, Delete

    // Read
    List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException;
    
    // method not needed
    //Map<String, Object> findRecordByKey(String tableName, String key) throws SQLException;
    
    // Update
    void updateRecordByKey(String tableName, String key, List<String> colNames, List<Object> colValues);
    
    // Create
    void createNewRecord(String tableName, List<String> colNames, List<Object> colValues)  throws SQLException;
    
    // Delete
    void deleteRecordByPrimaryKey(String tableName, String primaryKeyColumnName, Object primaryKeyValue)
             throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException;
    
}
