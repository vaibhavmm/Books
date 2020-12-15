package com.needle.books.service;


import com.needle.books.entity.Book;
import com.needle.books.repository.BooksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BooksServiceUnitTests {

    @InjectMocks
    private BooksService booksService;

    @Mock
    private BooksRepository repository;


    @Test
    public  void  shouldGetCorrectBookDetail(){
        Book book = getBookObj();
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        Book expectedBook = booksService.getBook(1);
        Assertions.assertEquals(expectedBook, book);
    }
    @Test
    public void shouldGetEmptyBook(){
        doReturn(Optional.of(new Book())).when(repository).findById(anyLong());
        Book book = booksService.getBook(5L);
        Assertions.assertEquals(new Book(), book);
    }

    @Test
    public void shouldGetAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(getBookObj());
        Page<Book> pagedTasks = new PageImpl(books);
        doReturn(pagedTasks).when(repository).findAll(PageRequest.of(0,1));
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks = booksService.getAllBooks(1,0);
        Assertions.assertEquals(expectedBooks,books);
    }

    @Test
    public void shouldGetNullForNoBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(null);
        Page<Book> pagedTasks = new PageImpl(books);
        doReturn(pagedTasks).when(repository).findAll(PageRequest.of(0,1));
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks = booksService.getAllBooks(1,0);
        Assertions.assertEquals(expectedBooks,books);
    }

    @Test
    public void saveBookTest()  {
        Book book = getBookObj();
        when(repository.existsById(book.getId())).thenReturn(false);
        doReturn(book).when(repository).save(book);
        Book expectedBook = booksService.saveBook(book);
        Assertions.assertEquals(expectedBook,book);
    }
    @Test
    public void saveBookReturnNullTest()  {
        Book book = getBookObj();
        when(repository.existsById(book.getId())).thenReturn(true);
        Book expectedBook = booksService.saveBook(book);
        Assertions.assertEquals(expectedBook,null);
    }

    @Test
    public void updateBookTest() throws Exception {
        Book book = getBookObj();
        when(repository.existsById(book.getId())).thenReturn(true);
        doReturn(book).when(repository).save(book);
        Book expectedBook = booksService.updateBook(book);
        Assertions.assertEquals(expectedBook,book);
    }

    @Test
    public void updateBookforNullTest() throws Exception {
        Book book = getBookObj();
        when(repository.existsById(book.getId())).thenReturn(false);
        Book expectedBook = booksService.updateBook(book);
        Assertions.assertEquals(expectedBook,null);
    }

    @Test
    public void deleteBookTest() throws Exception {
        Book book = getBookObj();
        when(repository.existsById(book.getId())).thenReturn(true);
        booksService.deleteBook(book.getId());
        verify(repository,times(1)).deleteById(book.getId());

    }
    @Test
    public void deleteInvalidBookTest() throws Exception {
        Book book = getBookObj();
        when(repository.existsById(book.getId())).thenReturn(false);
        booksService.deleteBook(book.getId());
        verify(repository,times(0)).deleteById(book.getId());

    }
    private Book getBookObj() {
        Book book = mock(Book.class);
        return book;
    }

}