package com.example.bookservice.service;

import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Title1", "Author1", "Desc", "IBN"),
                new Book(2L, "Title2", "Author2", "Desc", "IBN")
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Book::getTitle).containsExactly("Title1", "Title2");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book(1L, "Title", "Author", "Desc", "IBN");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Title");
    }

    @Test
    void shouldAddNewBook() {
        Book book = new Book(null, "Title", "Author", "Desc", "IBN");
        Book saved = new Book(1L, "Title", "Author", "Desc", "IBN");

        when(bookRepository.save(book)).thenReturn(saved);

        Book result = bookService.saveBook(book);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Title");
    }

    @Test
    void shouldUpdateExistingBook() {
        Book existing = new Book(1L, "Old", "OldAuthor", "Desc", "IBN");
        Book updated = new Book(1L, "New", "NewAuthor", "Desc", "IBN");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(updated);

        Optional<Book> result = bookService.updateBook(1L, updated);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("New");
    }

    @Test
    void shouldDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        boolean deleted = bookService.deleteBook(1L);

        assertThat(deleted).isTrue();
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldNotDeleteNonExistingBook() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        boolean deleted = bookService.deleteBook(1L);

        assertThat(deleted).isFalse();
        verify(bookRepository, never()).deleteById(1L);
    }
}