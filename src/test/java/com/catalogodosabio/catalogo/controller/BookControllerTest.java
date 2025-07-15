package com.catalogodosabio.catalogo.controller;

import com.catalogodosabio.catalogo.exception.ResourceNotFoundException;
import com.catalogodosabio.catalogo.model.Book;
import com.catalogodosabio.catalogo.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("GET /books/{id} - Success")
    void getBookById_shouldReturnBook_whenExists() throws Exception {
        Book book = new Book(1L, "Title", "Author", "Genre", "Description");
        Mockito.when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title")));
    }

    @Test
    @DisplayName("GET /books/{id} - Not Found")
    void getBookById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(bookService.findById(999L)).thenThrow(new ResourceNotFoundException("Código 999 não encontrado."));

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("999")));
    }

    @Test
    @DisplayName("GET /books/genre/{genre} - Success")
    void getBooksByGenre_shouldReturnBooks() throws Exception {
        List<Book> books = List.of(new Book(1L, "A", "B", "Fiction", "Desc"));
        Mockito.when(bookService.findByGenre("fiction")).thenReturn(books);

        mockMvc.perform(get("/books/genre/fiction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].genre", is("Fiction")));
    }

    @Test
    @DisplayName("GET /books/author/{author} - Success")
    void getBooksByAuthor_shouldReturnBooks() throws Exception {
        List<Book> books = List.of(new Book(1L, "Title", "Carlos Jr", "Drama", "Desc"));
        Mockito.when(bookService.findByAuthor("jr")).thenReturn(books);

        mockMvc.perform(get("/books/author/jr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author", containsString("Jr")));
    }

    @Test
    @DisplayName("GET /books - Paginated")
    void getAllBooks_shouldReturnPaginatedBooks() throws Exception {
        List<Book> books = List.of(new Book(1L, "T1", "A1", "G1", "D1"), new Book(2L, "T2", "A2", "G2", "D2"));
        Page<Book> page = new PageImpl<>(books);
        Mockito.when(bookService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/books?page=0&size=2&sortBy=title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title", is("T1")));
    }
}
