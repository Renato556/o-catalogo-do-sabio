package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void findAllSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book1 = new Book("123", "Teste", "Autor", genres1, "Editora");

        ArrayList<String> genres2 = new ArrayList<>(Arrays.asList("Ficção", "Terror"));
        Book book2 = new Book("456", "Teste2", "Autor2", genres2, "Editora2");

        ArrayList<Book> bookArrayList = new ArrayList<>(Arrays.asList(book1, book2));

        // GIVEN
        when(bookRepository.findAll()).thenReturn(bookArrayList);
        // WHEN
        List<Book> result = bookService.findAll();
        // THEN
        verify(bookRepository, times(1)).findAll();
        assertEquals(bookArrayList, result);
    }

    @Test
    void findByIdSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book = new Book("123", "Teste", "Autor", genres1, "Editora");
        book.setId("randomId");

        // GIVEN
        when(bookRepository.findById("randomId")).thenReturn(Optional.of(book));
        // WHEN
        Book result = bookService.findById("randomId");
        // THEN
        verify(bookRepository, times(1)).findById("randomId");
        assertEquals(book, result);
    }
}