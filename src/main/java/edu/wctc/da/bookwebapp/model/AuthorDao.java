/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author David Arnell
 */
public class AuthorDao implements AuthorDaoStrategy {

    // DbStrategy, used in Constructor to ensure class has one
    private DbStrategy db;

    // openConnection info used in getAuthorList
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    // cannot create an AuthorDao object without a DbStrategy
    public AuthorDao(DbStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {

        // the first part of finding the records is opening the connection
        db.openConnection(driverClass, url, userName, password);

        //List of Maps of all the records in the table
        List<Map<String, Object>> records = db.findAllRecords("author", 500);

        // List to store the Author Objects
        List<Author> authors = new ArrayList<>();

        // for each record in records
        for (Map<String, Object> rec : records) {
            Author author = new Author();

            // gets the author_id as an object, parses it to a String, and parses it to an integer
            int authorId = Integer.parseInt(rec.get("author_id").toString());

            //sets the authorId value for the Author object created in this for loop
            author.setAuthorId(authorId);

            // gets the author name and checks if it's null
            String authorName = rec.get("author_name").toString();
            author.setAuthorName(authorName != null ? authorName : "");

            //gets the date added Date Object, just casts it to a Date Object
            Date dateAdded = (Date) rec.get("date_added");
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
    public Author findAuthorByKey(int key)  throws ClassNotFoundException, SQLException {
        List<Author> authors = getAuthorList();
        Author author = authors.get(key);
        
        return author;
    }

    @Override
    public void updateAuthorByKey(Author author, int key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method takes the parameters for an author object and inserts them 
     * into a new Author object
     * @param author
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public void createNewAuthor(Author author) throws ClassNotFoundException, SQLException{
        // String tableName, List<String> colNames, List<Object> colValues
        
        // sets the value for the tableName
        String tableName = "author";
        
        List<String> colNames = new ArrayList<>();
        // the authorId will be created automatically by the database
        colNames.add("author_id");
        colNames.add("author_name");
        colNames.add("date_added");
        
        List<Object> colValues = new ArrayList<>();
        colValues.add(author.getAuthorName());
        colValues.add(author.getDateAdded());
        
        // creates the new record with the new Author object
        db.createNewRecord(tableName, colNames, colValues);
 
    }

    @Override
    public void deleteAuthorByPrimaryKey(String key)
            throws ClassNotFoundException, SQLException, NumberFormatException{
        
        // the first part of finding the records is opening the connection
        db.openConnection(driverClass, url, userName, password);
        
        Integer primaryKeyValue = Integer.parseInt(key);
        
        db.deleteRecordByPrimaryKey("author", key);
        
        db.closeConection();
        
        
    }
    
    public DbStrategy getDb() {
        return db;
    }

    public void setDb(DbStrategy db) {
        this.db = db;
    }
    
    // throws generic exception to handle everything for the dbstrategy
    public static void main(String[] args) throws Exception {
        AuthorDao dao = new AuthorDao(
                new MySqlDbStrategy(), // db strategy passed into the AuthorDao constructor
                "com.mysql.jdbc.Driver", //driver to use
                "jdbc:mysql://localhost:3306/book", // url of the database
                "root", "admin"); // username and password
        
        // creates the List of Author Objects from the dao method "getAuthorList"
        // this method will work with any DbStrategy
        List<Author> authors = dao.getAuthorList();
        
        // prints out a the authors unformatted
        System.out.println(authors);
        
    }

}
