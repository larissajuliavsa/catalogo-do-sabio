package com.catalogodosabio.catalogo.controller;

import com.catalogodosabio.catalogo.model.Book;
import com.catalogodosabio.catalogo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

//    @GetMapping
//    public ResponseEntity<List<Book>> getAllBooks() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(service.findByGenre(genre));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(service.findByAuthor(author));
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(service.findAll(pageable));
    }

}
