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
        this.authorID = authorID;
    }
    
    private int authorID;
    private String authorName;
    private Date dateAdded;

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
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
        hash = 71 * hash + this.authorID;
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
        if (this.authorID != other.authorID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorID=" + authorID + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
    
}
