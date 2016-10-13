/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author David Arnell
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable{

    // DbStrategy, used in Constructor to ensure class has one
    @Inject
    private DbStrategy db;
    
    //private DataSource ds;

    // openConnection info used in getAuthorList
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    final private String AUTHOR_TABLE = "author";
    final private String AUTHOR_NAME_COLUMN = "author_name";
    final private String AUTHOR_ID_COLUMN = "author_id";
    final private String AUTHOR_DATE_ADDED_COLUMN = "date_added";

    public AuthorDao() {
    }
    
//    @Override
//    public void initDao(){
//        
//    }
    
    /**
     * initDao method to set the driver, URL, user, and password
     * @param driver
     * @param url
     * @param user
     * @param password 
     */
    @Override
    public void initDao(String driver, String url, String user, String password){
        setDriverClass(driver);
        setUrl(url);
        setUserName(user);
        setPassword(password);
    }

    // need to change this so it's String primaryKey
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {

        // the first part of finding the records is opening the connection
        db.openConnection(driverClass, url, userName, password);

        //List of Maps of all the records in the table
        List<Map<String, Object>> records = db.findAllRecords(AUTHOR_TABLE, 500);

        // List to store the Author Objects
        List<Author> authors = new ArrayList<>();

        // for each record in records
        for (Map<String, Object> rec : records) {
            Author author = new Author();

            // gets the author_id as an object, parses it to a String, and parses it to an integer
            int authorId = Integer.parseInt(rec.get(AUTHOR_ID_COLUMN).toString());

            //sets the authorId value for the Author object created in this for loop
            author.setAuthorId(authorId);
            
            String authorName;
            
            // check to see if the authorName is null
            if(rec.get(AUTHOR_NAME_COLUMN).toString() == null){
                authorName = "NAME_NOT_ENTERED";
                author.setAuthorName(authorName);
            } else {
                authorName = rec.get(AUTHOR_NAME_COLUMN).toString();
                author.setAuthorName(authorName);
            }

            // gets the author name and checks if it's null
            //String authorName = rec.get(AUTHOR_NAME_COLUMN).toString();
            //String authorName = "test";
            //author.setAuthorName(authorName != null ? authorName : "");
            //authorName="getName";
            
            //author.setAuthorName(authorName);

            //gets the date added Date Object, just casts it to a Date Object
            Date dateAdded = (Date) rec.get(AUTHOR_DATE_ADDED_COLUMN);
            author.setDateAdded(dateAdded);

            // adds the new Author to the List
            authors.add(author);
        }

        // the last thing is closing the connection
        db.closeConection();

        // returns the List of Author Objects after closing the connection
        return authors;
    }

    /**
     * This method uses the get author list method to get a list of the authors
     * Then the method gets the author object from the list by the authorId
     * @param key
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */    
    @Override
    public Author findAuthorByPrimaryKey(String authorId)  
            throws ClassNotFoundException, SQLException,NumberFormatException {
        
        db.openConnection(driverClass, url, userName, password);
        
        // gets the int value for the primary key
        // the key for the authors list is an integer
        int primaryKey = Integer.parseInt(authorId);
        
        List<Author> authors = getAuthorList();
        
        Author author = authors.get(primaryKey);
        
        db.closeConection();
        
        return author;
    }

    // need to change this so it's String primaryKey
    @Override
    public void updateAuthorByPrimaryKey(String authorId, String authorName, String dateAdded) throws ClassNotFoundException, SQLException {
        //updateRecordByKey(String tableName, Object primaryKeyValue,
           // String primaryKeyColumnName, List<String> colNames, List<Object> colValues
           
           db.openConnection(driverClass, url, userName, password);
        
        // String tableName, List<String> colNames, List<Object> colValues
        
        // sets the value for the tableName
        String tableName = AUTHOR_TABLE;
        Object primaryKeyValue = authorId;
        String primaryKeyColumnName = AUTHOR_ID_COLUMN;
        
        List<String> colNames = new ArrayList<>();
        colNames.add(AUTHOR_ID_COLUMN);
        colNames.add(AUTHOR_NAME_COLUMN);
        colNames.add(AUTHOR_DATE_ADDED_COLUMN);
        
        List<Object> colValues = new ArrayList<>();
        colValues.add(authorId);
        colValues.add(authorName);
        colValues.add(dateAdded);
        
        db.updateRecordByKey(tableName, primaryKeyValue, primaryKeyColumnName, colNames, colValues);
        
        db.closeConection();
    }

    /**
     * This method takes the parameters for an author object and inserts them 
     * into a new Author object
     * @param author
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public void createNewAuthor(String authorName) throws ClassNotFoundException, SQLException{
        
        db.openConnection(driverClass, url, userName, password);
        
        Date dateAdded = new Date();
        
        // sets the value for the tableName
        String tableName = AUTHOR_TABLE;
        
        List<String> colNames = new ArrayList<>();
        // the authorId will be created automatically by the database
        //colNames.add("author_id");
        colNames.add(AUTHOR_NAME_COLUMN);
        colNames.add(AUTHOR_DATE_ADDED_COLUMN);
        
        List<Object> colValues = new ArrayList<>();
        colValues.add(authorName);
        colValues.add(dateAdded);
        
        // creates the new record with the new Author object
        db.createNewRecord(tableName, colNames, colValues);
         
        db.closeConection();
    }

    // need to change this so it's String primaryKey
    @Override
    public void deleteAuthorByPrimaryKey(String key)
            throws ClassNotFoundException, SQLException, NumberFormatException{
        
        // the first part of finding the records is opening the connection
        db.openConnection(driverClass, url, userName, password);
        
        // if this method fails, a NumberFormatException is thrown
        Integer primaryKeyValue = Integer.parseInt(key);
        
        db.deleteRecordByPrimaryKey(AUTHOR_TABLE, AUTHOR_ID_COLUMN, primaryKeyValue);
        
        db.closeConection();
        
        
    }
    
    public DbStrategy getDb() {
        return db;
    }

    public void setDb(DbStrategy db) {
        this.db = db;
    }
    
    
    
    // throws generic exception to handle everything for the dbstrategy
//    public static void main(String[] args) throws Exception {
//        AuthorDao dao = new AuthorDao(
//                new MySqlDbStrategy(), // db strategy passed into the AuthorDao constructor
//                "com.mysql.jdbc.Driver", //driver to use
//                "jdbc:mysql://localhost:3306/book", // url of the database
//                "root", "admin"); // username and password
//        
//        // creates the List of Author Objects from the dao method "getAuthorList"
//        // this method will work with any DbStrategy
//        List<Author> authors = dao.getAuthorList();
//        
//        // prints out a the authors unformatted
//        System.out.println(authors);
//        
//    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
