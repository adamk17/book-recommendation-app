package com.example.reviewservice.service.client;

import com.example.reviewservice.config.FeignAuthConfig;
import com.example.reviewservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "${app.books.url}", configuration = FeignAuthConfig.class)
public interface BookClient {

    @GetMapping("/api/v1/books/{id}")
    BookDto getBookById(@PathVariable Long id);
}
