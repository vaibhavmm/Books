package com.needle.books.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(targetEntity = Book.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "books_and_authors",joinColumns = @JoinColumn(name = "author_id"),inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonIgnore
    private List<Book> books;
    @CreationTimestamp
    @JsonIgnore
    private Date timestamp;

    public Author(long id,String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
