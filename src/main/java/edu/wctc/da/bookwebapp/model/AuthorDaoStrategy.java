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
    Author findAuthorByKey(int key) 
            throws ClassNotFoundException, SQLException;
    
    void updateAuthorByKey(Author author, int key) throws ClassNotFoundException, SQLException;
    
    void createNewAuthor(Author author) throws ClassNotFoundException, SQLException;
    
    void deleteAuthorByPrimaryKey(int key) throws ClassNotFoundException, SQLException, NumberFormatException;
    
}
