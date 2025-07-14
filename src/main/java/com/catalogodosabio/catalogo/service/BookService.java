package com.catalogodosabio.catalogo.service;

import com.catalogodosabio.catalogo.model.Book;
import com.catalogodosabio.catalogo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Cacheable("books")
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "bookById", key = "#id")
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Cacheable(value = "booksByGenre", key = "#genre.toLowerCase()")
    public List<Book> findByGenre(String genre) {
        return repository.findByGenreIgnoreCase(genre);
    }

    @Cacheable(value = "booksByAuthor", key = "#author.toLowerCase()")
    public List<Book> findByAuthor(String author) {
        return repository.findByAuthorIgnoreCase(author);
    }

    public Page<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
