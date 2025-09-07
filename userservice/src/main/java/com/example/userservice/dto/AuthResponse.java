package com.example.userservice.dto;

public record AuthResponse(String accessToken, Long userId, String username, String email) {}
