/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Melnikov
 */
@Entity
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Book book;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date giveBookToReaderDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date returnBook;

    public History() {
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getGiveBookToReaderDate() {
        return giveBookToReaderDate;
    }

    public void setGiveBookToReaderDate(Date giveBookToReaderDate) {
        this.giveBookToReaderDate = giveBookToReaderDate;
    }

    public Date getReturnBook() {
        return returnBook;
    }

    public void setReturnBook(Date returnBook) {
        this.returnBook = returnBook;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.user);
        hash = 47 * hash + Objects.hashCode(this.book);
        hash = 47 * hash + Objects.hashCode(this.giveBookToReaderDate);
        hash = 47 * hash + Objects.hashCode(this.returnBook);
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
        final History other = (History) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        if (!Objects.equals(this.giveBookToReaderDate, other.giveBookToReaderDate)) {
            return false;
        }
        if (!Objects.equals(this.returnBook, other.returnBook)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "History{"
                + "id=" + id 
                + ", user=" + user.getFirstname()
                + " " + user.getLastname()
                + ", book=" + book.getTitle()
                + ", giveBookToReaderDate=" + giveBookToReaderDate 
                + ", returnBook=" + returnBook 
                + '}';
    }

   public StringProperty giveUpDateProperty() {
        return new SimpleStringProperty(this.giveBookToReaderDate.toString());
   }
   public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(id));
   }
}
