/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author David Arnell
 */
public class AuthorService {
    
    private AuthorDaoStrategy dao;

    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }    
    
    public List<Author> getAuthors() throws ClassNotFoundException, SQLException {
        
        return dao.getAuthorList();

//        Author author1 = new Author();
//        Author author2 = new Author();
//        Author author3 = new Author();
//
//        author1.setAuthorId(1);
//        author2.setAuthorId(2);
//        author3.setAuthorId(3);
//
//        author1.setAuthorName("Ice T");
//        author2.setAuthorName("Ice Cube");
//        author3.setAuthorName("Vanilla Ice");
//
//        author1.setDateAdded(Calendar.getInstance().getTime());
//        author2.setDateAdded(Calendar.getInstance().getTime());
//        author3.setDateAdded(Calendar.getInstance().getTime());
//
//        List<Author> authorList = new ArrayList<>();
//        authorList.add(author1);
//        authorList.add(author2);
//        authorList.add(author3);

        //return authorList;
    }
    
    /**
     * This methos gets an author by the authorId by using the getAuthorList function
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Author getAuthorById(int authorId) throws ClassNotFoundException, SQLException{
        Author author = new Author();
        
        List<Author> authors = dao.getAuthorList();
        
        author = authors.get(authorId);
        
        return author;
    }
    
    public static void main(String[] args) throws Exception {
        // creates dao for the AuthorService
        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), // db strategy passed into the AuthorDao constructor
                "com.mysql.jdbc.Driver", //driver to use
                "jdbc:mysql://localhost:3306/book", // url of the database
                "root", "admin");
        
        AuthorService as = new AuthorService(dao);
        
        //List<Author> authorList = as.getAuthors();
        
        //System.out.println(authorList);
        
        Author a = as.getAuthorById(1);
        System.out.println(a);
    }

}
