package com.needle.books;

import com.needle.books.entity.Author;
import com.needle.books.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = BooksApplication.class
)

@TestPropertySource(locations = {"classpath:application-test.properties"})
public class BooksControllerIntegrationTest {

    private static final String baseURL = "http://localhost:8080/needle/books";

    @Autowired
    private TestRestTemplate template;

    @Sql({"classpath:V1__books.sql"})
    @Test
    public  void  saveBooksTest(){

        Author author = new Author("Dale","", "Carnegie");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        Book book = new Book("ISBN-978-91-7599","How to Win Friends & Influence People",authors,"Motivational","FingerPrint Pubishing",278);
        ResponseEntity<Book> responseEntity = this.template.postForEntity(baseURL + "/save",book,Book.class);
        assertEquals(200,responseEntity.getStatusCodeValue());

    }

}



