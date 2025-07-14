package com.example.bookservice.controllers;

import com.example.bookservice.entity.Book;
import com.example.bookservice.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository repository;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/api/v1/books";
        repository.deleteAll();
    }

    @Test
    void shouldAddAndGetBook() {
        Book book = new Book(null, "Title", "Author", "Desc", "ISBN");
        ResponseEntity<Book> post = restTemplate.postForEntity(baseUrl, book, Book.class);

        assertEquals(HttpStatus.CREATED, post.getStatusCode());
        assertNotNull(post.getBody());
        Long id = post.getBody().getId();

        ResponseEntity<Book> get = restTemplate.getForEntity(baseUrl + "/" + id, Book.class);
        assertEquals(HttpStatus.OK, get.getStatusCode());
        assertEquals("Title", Objects.requireNonNull(get.getBody()).getTitle());
    }

    @Test
    void shouldReturnNotFoundForMissingBook() {
        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl + "/999", Book.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldDeleteBook() {
        Book book = repository.save(new Book(null, "ToDelete", "Author", "Desc", "ISBN"));

        ResponseEntity<Void> delete = restTemplate.exchange(baseUrl + "/" + book.getId(), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, delete.getStatusCode());
    }
}
