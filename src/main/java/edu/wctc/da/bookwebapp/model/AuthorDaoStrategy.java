/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David Arnell
 */
public interface AuthorDaoStrategy {
    
    public void initDao(String driver, String url, String user, String password);

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
    // everything given to the controller as a String
    Author findAuthorByPrimaryKey(String authorId) 
            throws ClassNotFoundException, SQLException, NumberFormatException;
    
    void updateAuthorByPrimaryKey(String authorId, String authorName, String dateAdded) 
            throws ClassNotFoundException, SQLException;
    
//    void createNewAuthor(Author author) throws ClassNotFoundException, SQLException;
    void createNewAuthor(String authorName) throws ClassNotFoundException, SQLException;
    
    void deleteAuthorByPrimaryKey(String authorId) throws ClassNotFoundException, SQLException, NumberFormatException;
    
}
