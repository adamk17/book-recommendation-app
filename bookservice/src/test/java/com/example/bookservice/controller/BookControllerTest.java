package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import com.example.bookservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book(1L, "Sample Title", "Sample Author", "Desc", "ISBN");
        reset(bookService);
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Sample Title"));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Title"));
    }

    @Test
    void shouldReturnNotFoundForMissingBook() throws Exception {
        when(bookService.getBookById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateBook() throws Exception {
        Book inputBook = new Book(null, "New Book", "New Author", "Desc", "ISBN");
        Book savedBook = new Book(1L, "New Book", "New Author", "Desc", "ISBN");

        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Book updatedBook = new Book(1L, "Updated", "Updated Author", "Desc", "ISBN");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.of(updatedBook));

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingBook() throws Exception {
        Book updatedBook = new Book(1L, "Updated", "Updated Author", "Desc", "ISBN");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(false);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public BookService bookService() {
            return mock(BookService.class);
        }
    }
}
