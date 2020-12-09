package com.needle.books.service;

import com.needle.books.entity.Book;
import com.needle.books.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksService {

    @Autowired
    private BooksRepository repository;

    public Book saveBook(Book book) {
        return repository.save(book);
    }
}
