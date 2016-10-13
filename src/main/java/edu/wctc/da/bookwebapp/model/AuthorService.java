/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author David Arnell
 */
@SessionScoped
public class AuthorService implements Serializable{

    @Inject
    private AuthorDaoStrategy dao;
    
    /**
     * default constructor required for injectable objects
     */
    public AuthorService() {
    }

//    public AuthorService(AuthorDaoStrategy dao) {
//        this.dao = dao;
//    }

    public List<Author> getAuthors() throws ClassNotFoundException, SQLException {

        return dao.getAuthorList();
    }

    /**
     * This method gets an author by the authorId by using the getAuthorList
     * function I was able to reuse methods already created for this specific
     * method
     * 
     * change it all to strings
     *
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Author getAuthorById(String authorId) throws ClassNotFoundException, SQLException {
        Author author = new Author();

        List<Author> authors = dao.getAuthorList();
        
        int intAuthorId = Integer.getInteger(authorId);

        author = authors.get(intAuthorId);

        return author;
    }

    public void deleteAuthorById(String authorId) throws ClassNotFoundException, SQLException {

        String primaryKey = String.valueOf(authorId);

        dao.deleteAuthorByPrimaryKey(primaryKey);
    }

    /**
     * 
     * @param authorId
     * @param authorName
     * @param dateAdded
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void updateAuthorById(String authorId, String authorName, String dateAdded) 
            throws ClassNotFoundException, SQLException {
        
        String sAuthorId = String.valueOf(authorId);        
        
        dao.updateAuthorByPrimaryKey(sAuthorId, authorName, dateAdded);
    }
    
    /**
     * 
     * @param authorName - String for the Authors first & last name
     * @param dateAdded - Date used for the date the author was made
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void createNewAuthor(String authorName) 
            throws ClassNotFoundException, SQLException{
        
        dao.createNewAuthor(authorName);
    }
    
    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }    

//    public static void main(String[] args) throws Exception {
//        // creates dao for the AuthorService
//        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), // db strategy passed into the AuthorDao constructor
//                "com.mysql.jdbc.Driver", //driver to use
//                "jdbc:mysql://localhost:3306/book", // url of the database
//                "root", "admin");
//
//        AuthorService as = new AuthorService(dao);
//
//        Date date = new Date();
//
//    }


}
