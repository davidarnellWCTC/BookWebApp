/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

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

    public AuthorService() {
    }

//    private Author author1 = new Author();
//    private Author author2 = new Author();
//    private Author author3 = new Author();
    public List<Author> getAuthors() {

        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();

        author1.setAuthorID(1);
        author2.setAuthorID(2);
        author3.setAuthorID(3);

        author1.setAuthorName("Ice T");
        author2.setAuthorName("Ice Cube");
        author3.setAuthorName("Vanilla Ice");

        author1.setDateAdded(Calendar.getInstance().getTime());
        author2.setDateAdded(Calendar.getInstance().getTime());
        author3.setDateAdded(Calendar.getInstance().getTime());

        List<Author> authorList = new ArrayList<>();
        authorList.add(author1);
        authorList.add(author2);
        authorList.add(author3);

        return authorList;
    }

}
