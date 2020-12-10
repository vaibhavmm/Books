package com.needle.books.controller;

import com.needle.books.entity.Book;
import com.needle.books.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("/get/{id}")
    public  ResponseEntity<Book> getBook(@PathVariable("id") long id){
        String accept = request.getHeader("Accept");
        if(accept!=null && accept.contains("application/json")){
            try{
                return  new ResponseEntity<Book>(service.getBook(id),HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id){
        String accept = request.getHeader("Accept");
        if(accept!= null && accept.contains(("application/json"))){
            try {
                service.deleteBook(id);
                return new ResponseEntity<Book>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public  ResponseEntity<List<Book>> getAllBooks(@RequestParam int limit, @RequestParam int offset){
        String accept = request.getHeader("Accept");
        if(accept!=null && accept.contains("application/json")){
            try{
                return  new ResponseEntity<List<Book>>(service.getAllBooks(limit, offset),HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        String accept = request.getHeader("Accept");
        if(accept!=null && accept.contains("application/json")){
            try{
                return new ResponseEntity<Book>(service.updateBook(book),HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
