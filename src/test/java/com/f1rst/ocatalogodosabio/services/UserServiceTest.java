package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.domain.entities.User;
import com.f1rst.ocatalogodosabio.repositories.UserRepository;
import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findByIdSuccessTest() {
        User user = new User("123", "Renato");;

        // GIVEN
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        // WHEN
        User result = userService.findById("123");
        // THEN
        assertEquals(result, user);
    }

    @Test
    void findByIdExceptionTest() {
        // GIVEN
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        // THEN
        assertThrows(ObjectNotFoundException.class, () -> userService.findById("123"));
    }

    @Test
    void addBookToLastSeenSuccessTest() {
        Book book = new Book("123456", "Clean Code", "Uncle Bob", List.of("Coding"), "any");
        User user = new User("123", "Renato");
        User expected = new User("123", "Renato");
        expected.addToLastSeen(book);

        // GIVEN
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(expected);
        // WHEN
        userService.addBookToLastSeen("123", book);
        // THEN
        assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void addBookToLastSeenBlankId() {
        Book book = new Book("123456", "Clean Code", "Uncle Bob", List.of("Coding"), "any");

        // WHEN
        userService.addBookToLastSeen("", book);
        // THEN
        assertAll(
                () -> verify(userRepository, times(0)).findById(anyString()),
                () -> verify(userRepository, times(0)).save(any(User.class))
        );
    }
}