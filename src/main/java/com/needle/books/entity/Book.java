package com.needle.books.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private long id;
    @Column(name = "serial_no")
    private String serialNo;
    private String name;
    @ManyToMany(targetEntity = Author.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "books_and_authors",joinColumns = @JoinColumn(name = "book_id"),inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
    private String genre;
    @Column(name = "publisher_name")
    private String publisherName;
    @Column(name = "total_pages")
    private int totalPages;
    @CreationTimestamp
    @JsonIgnore
    private Date timestamp;

    public Book(long id,String serialNo, String name, List<Author> authors, String genre, String publisherName, int totalPages) {
       this.id = id;
        this.serialNo = serialNo;
        this.name = name;
        this.authors = authors;
        this.genre = genre;
        this.publisherName = publisherName;
        this.totalPages = totalPages;
    }
}
