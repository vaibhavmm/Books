package com.needle.books.service;

import com.needle.books.entity.Book;
import com.needle.books.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BooksService {

    @Autowired
    private BooksRepository repository;

    @Transactional
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public Book getBook(long id) {
        return repository.findById(id).orElse(new Book());
    }

    @Transactional
    public void deleteBook(long id) throws Exception {
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
        }
    }

    public List<Book> getAllBooks(int limit, int offset) throws Exception {
        if(limit == 0 ) return null;
        List<Book> books = repository.findAll(PageRequest.of((offset/limit),limit)).getContent();
        if(books.isEmpty()){
            return null;
        }
        return books;
    }

    @Transactional
    public Book updateBook(Book book) throws Exception {
        if(repository.findById(book.getId()).isPresent()){
            deleteBook(book.getId());
            return repository.save(book);
        }else{
            return null;
        }
    }
}
