package com.catalogodosabio.catalogo.service;

import com.catalogodosabio.catalogo.exception.ResourceNotFoundException;
import com.catalogodosabio.catalogo.model.Book;
import com.catalogodosabio.catalogo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Book> books = List.of(new Book(1L, "Livro A", "Autor A", "Gênero A", "Descrição"));
        when(repository.findAll()).thenReturn(books);

        List<Book> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Livro A", result.get(0).getTitle());
    }

    @Test
    void testFindById_found() {
        Book book = new Book(1L, "Livro A", "Autor A", "Gênero A", "Descrição");
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Book result = service.findById(1L);
        assertEquals("Livro A", result.getTitle());
    }

    @Test
    void testFindById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void testFindByGenre_found() {
        when(repository.findByGenreContainingIgnoreCase("fic")).thenReturn(List.of(
                new Book(1L, "Livro A", "Autor A", "Fiction", "Descrição")
        ));

        List<Book> result = service.findByGenre("fic");
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByGenre_notFound() {
        when(repository.findByGenreContainingIgnoreCase("exemplo")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.findByGenre("exemplo"));
    }

    @Test
    void testFindByAuthor_found() {
        when(repository.findByAuthorContainingIgnoreCase("jr")).thenReturn(List.of(
                new Book(1L, "Livro A", "Carlos Jr", "Romance", "Descrição")
        ));

        List<Book> result = service.findByAuthor("jr");
        assertEquals("Carlos Jr", result.get(0).getAuthor());
    }

    @Test
    void testFindByAuthor_notFound() {
        when(repository.findByAuthorContainingIgnoreCase("exemplo")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.findByAuthor("exemplo"));
    }

    @Test
    void testFindAllPaginated() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Book> books = List.of(new Book(1L, "Livro A", "Autor A", "Romance", "Desc"));
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(books));

        Page<Book> result = service.findAll(pageable);
        assertEquals(1, result.getTotalElements());
    }
}
