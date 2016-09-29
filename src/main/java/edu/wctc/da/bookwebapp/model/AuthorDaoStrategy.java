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
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
    // everything given to the controller as a String
    Author findAuthorByPrimaryKey(String key) 
            throws ClassNotFoundException, SQLException, NumberFormatException;
    
    void updateAuthorByPrimaryKey(Author author, int key) 
            throws ClassNotFoundException, SQLException;
    
//    void createNewAuthor(Author author) throws ClassNotFoundException, SQLException;
    void createNewAuthor(Author author) throws ClassNotFoundException, SQLException;
    
    void deleteAuthorByPrimaryKey(String key) throws ClassNotFoundException, SQLException, NumberFormatException;
    
}
