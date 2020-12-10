package com.needle.books.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "serial_no")
    private String serialNo;
    private String name;
    @ManyToMany(targetEntity = Author.class,cascade = CascadeType.ALL)
    @JoinTable(name = "books_and_authors",joinColumns = @JoinColumn(name = "book_id"),inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
    private String genre;
    @Column(name = "publisher_name")
    private String publisherName;
    @Column(name = "total_pages")
    private int totalPages;
    @CreationTimestamp
    private Date timestamp;

    public Book(String serialNo, String name, List<Author> authors, String genre, String publisherName, int totalPages) {
        this.serialNo = serialNo;
        this.name = name;
        this.authors = authors;
        this.genre = genre;
        this.publisherName = publisherName;
        this.totalPages = totalPages;
    }
}
