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
        }else{
            throw new Exception("No such book found");
        }
    }

    public List<Book> getAllBooks(int limit, int offset) throws Exception {
        if(limit == 0) throw new Exception("Limit cannot be zero");
        List<Book> books = repository.findAll(PageRequest.of(offset/limit,limit)).getContent();
        if(books.isEmpty()){
            throw new Exception("Books Database is empty");
        }
        return books;
    }

    @Transactional
    public Book updateBook(Book book) throws Exception {
        deleteBook(book.getId());
        return repository.save(book);
    }
}
