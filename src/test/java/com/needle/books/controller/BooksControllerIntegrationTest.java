package com.needle.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.needle.books.BooksApplication;
import com.needle.books.entity.Book;
import com.needle.books.repository.BooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = BooksApplication.class
)
@EnableAutoConfiguration
@TestPropertySource(locations = {"classpath:application-test.yml"})

public class BooksControllerIntegrationTest {


    @LocalServerPort
    private Integer port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BooksRepository booksRepository;

    private static String projectPath = Paths.get("").toAbsolutePath().normalize().toString();

    private ResponseEntity testRetrieveBooks(HttpMethod method, Integer port, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        RestTemplate template = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(headers);
        return template.exchange(String.format("http://localhost:%d/needle/books/%s", port, url), method, httpEntity, Book.class);
    }

    private ResponseEntity testPersistBooks(HttpMethod method, Integer port, String url, Book body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        RestTemplate template = new RestTemplate();
        HttpEntity<Book> httpEntity = new HttpEntity<>(body, headers);
        return template.exchange(String.format("http://localhost:%d/needle/books/%s", port, url), method, httpEntity, Book.class);
    }

    private ResponseEntity testRetrieveListOfBooks(HttpMethod method, Integer port, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        RestTemplate template = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(headers);
        return template.exchange(String.format("http://localhost:%d/needle/books/%s", port, url), method, httpEntity, new ParameterizedTypeReference<List<Book>>() {
        });
    }

    @Sql({"classpath:schema.sql"})
    @Test
    public void getCorrectBookDetailsTests() throws IOException {
        Book expectedResponse = objectMapper.readValue(new File(projectPath + "/src/test/resources/book.json"), Book.class);
        ResponseEntity<Book> responseEntity = testRetrieveBooks(HttpMethod.GET, port, "get/1");
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse,responseEntity.getBody());
    }

    @Test
    public void getNotPresentDetailsTests(){
        Book book = new Book();
        ResponseEntity<List<Book>> responseEntity = testRetrieveBooks(HttpMethod.GET,port,"get/30");
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(book,responseEntity.getBody());

    }

    @Test
    public void getAllBooksTests() throws IOException {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks = Arrays.asList(objectMapper.readValue(new File(projectPath + "/src/test/resources/allbooks.json"), Book[].class));
        ResponseEntity responseEntity = testRetrieveListOfBooks(HttpMethod.GET,port,"get?limit=1&&offset=0");
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(expectedBooks,responseEntity.getBody());
    }

    @Test
    public void getNoBodyBooksTests() throws IOException {
        ResponseEntity<Book> responseEntity = testRetrieveBooks(HttpMethod.GET,port,"get?limit=1&&offset=1");
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }
    @Test
    public void getNoBodyForLimitZeroBooksTests() throws IOException {
        ResponseEntity<Book> responseEntity = testRetrieveBooks(HttpMethod.GET,port,"get?limit=0&&offset=0");
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Sql({"classpath:deletebookdata.sql"})
    @Test
    public void deleteBookTest(){
        ResponseEntity responseEntity = testRetrieveBooks(HttpMethod.DELETE,port,"/delete/2");
        assertEquals(200,responseEntity.getStatusCodeValue());
        Book expectedBook = booksRepository.findById(2L).orElse(null);
        assertEquals(expectedBook,responseEntity.getBody());
    }

    @Test
    public void saveNewBookTest() throws IOException {
        Book expectedResponse = objectMapper.readValue(new File(projectPath + "/src/test/resources/saveBook.json"), Book.class);
        ResponseEntity responseEntity = testPersistBooks(HttpMethod.POST,port,"/save",expectedResponse);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse,responseEntity.getBody());
        assertTrue(booksRepository.findById(3L).isPresent());

    }

    @Test
    public void updateBookTest() throws IOException {
        Book expectedResponse = objectMapper.readValue(new File(projectPath + "/src/test/resources/updatebook.json"), Book.class);
        ResponseEntity responseEntity = testPersistBooks(HttpMethod.PUT,port,"/update",expectedResponse);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse,responseEntity.getBody());

    }

    @Test
    public void updateUnAvailableBookTest() throws IOException {
        Book book = new Book();
        ResponseEntity responseEntity = testPersistBooks(HttpMethod.PUT,port,"/update",book);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());

    }


}



