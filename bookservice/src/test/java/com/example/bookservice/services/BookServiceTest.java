package com.example.bookservice.services;

import com.example.bookservice.entity.Book;
import com.example.bookservice.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    void shouldReturnAllBooks() {
        List<Book> books = List.of(new Book(1L, "Title", "Author", "Desc", "ISBN"));
        when(repository.findAll()).thenReturn(books);

        List<Book> result = service.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book(1L, "Title", "Author", "Desc", "ISBN");
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = service.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("Title", result.get().getTitle());
    }

    @Test
    void shouldSaveBook() {
        Book book = new Book(null, "New", "Author", "Desc", "ISBN");
        Book saved = new Book(1L, "New", "Author", "Desc", "ISBN");
        when(repository.save(book)).thenReturn(saved);

        Book result = service.saveBook(book);
        assertNotNull(result.getId());
        assertEquals("New", result.getTitle());
    }

    @Test
    void shouldUpdateBook() {
        Book existing = new Book(1L, "Old", "Author", "Desc", "ISBN");
        Book updated = new Book(null, "Updated", "Author", "Desc", "ISBN");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Book.class))).thenReturn(new Book(1L, "Updated", "Author", "Desc", "ISBN"));

        Optional<Book> result = service.updateBook(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().getTitle());
    }

    @Test
    void shouldDeleteBook() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boolean result = service.deleteBook(1L);
        assertTrue(result);
    }
}