package com.catalogodosabio.catalogo.service;

import com.catalogodosabio.catalogo.exception.ResourceNotFoundException;
import com.catalogodosabio.catalogo.model.Book;
import com.catalogodosabio.catalogo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Cacheable("books")
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "bookById", key = "#id")
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Código " + id + " não encontrado."));
    }

    @Cacheable(value = "booksByGenre", key = "#genre.toLowerCase()")
    public List<Book> findByGenre(String genre) {
        List<Book> books = repository.findByGenreIgnoreCase(genre);
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Gênero não encontrado: " + genre);
        }
        return books;
    }

    @Cacheable(value = "booksByAuthor", key = "#author.toLowerCase()")
    public List<Book> findByAuthor(String author) {
        List<Book> books = repository.findByAuthorIgnoreCase(author);
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Autor não encontrado: " + author);
        }
        return books;
    }

    public Page<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
