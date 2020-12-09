package com.needle.books.controller;

import com.needle.books.entity.Book;
import com.needle.books.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/needle/books")
public class BooksController {

    public HttpServletRequest request;

    public BooksController(HttpServletRequest request){
        this.request = request;
    }

    @Autowired
    private BooksService service;

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        String accept = request.getHeader("Accept");
        if(accept!=null && accept.contains("application/json")){
            try{
                return new ResponseEntity<Book>(service.saveBook(book), HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
