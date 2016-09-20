/*
 * 
 */
package edu.wctc.da.bookwebapp.model;

import java.util.Date;

/**
 *
 * @author David Arnell
 */
public class Author {

    public Author() {
    }

    public Author(int authorID) {
        this.authorId = authorID;
    }
    
    private int authorId;
    private String authorName;
    private Date dateAdded;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.authorId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorId != other.authorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorID=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
    
}
