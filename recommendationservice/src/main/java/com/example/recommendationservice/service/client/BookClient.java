package com.example.recommendationservice.service.client;

import com.example.recommendationservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "book-service", url = "${app.books.url}")
public interface BookClient {
    @GetMapping("/api/v1/books")
    List<BookDto> getAllBooks();
}
