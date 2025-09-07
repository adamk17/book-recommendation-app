package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        List<User> users = List.of(new User(1L, "john", "john@example.com", "john john", "password", null));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnUserById() {
        User user = new User(1L, "john", "john@example.com", "john john", "password", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertThat(result.getUsername()).isEqualTo("john");
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldCreateUser() {
        UserDto dto = new UserDto("john", "john@example.com", "john john", "password");
        User saved = new User(1L, "john", "john@example.com", "john john", "password", null);
        when(userRepository.save(any())).thenReturn(saved);

        User result = userService.createUser(dto);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldUpdateUser() {
        User existing = new User(1L, "old", "old@example.com", "john john", "password", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        User updated = new User(1L, "new", "new@example.com", "john john", "password", null);
        User result = userService.updateUser(1L, updated);

        assertThat(result.getUsername()).isEqualTo("new");
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
