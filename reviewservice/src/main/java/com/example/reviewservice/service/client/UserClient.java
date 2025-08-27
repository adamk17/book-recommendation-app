package com.example.reviewservice.service.client;

import com.example.reviewservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${app.users.url}")
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    UserDto getUserById(@PathVariable Long id);
}
