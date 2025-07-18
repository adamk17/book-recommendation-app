package com.example.reviewservice.service.client;

import com.example.reviewservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "http://localhost:8080")
public interface BookClient {

    @GetMapping("/api/v1/books/{id}")
    BookDto getBookById(@PathVariable Long id);
}
