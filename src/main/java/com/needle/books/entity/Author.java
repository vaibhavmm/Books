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
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(targetEntity = Book.class,cascade = CascadeType.ALL)
    @JoinTable(name = "books_and_authors",joinColumns = @JoinColumn(name = "author_id"),inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;
    @CreationTimestamp
    private Date timestamp;
}
