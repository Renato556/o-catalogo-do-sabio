package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.repositories.BookRepository;
import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        Pageable pageable = PageRequest.of(1, 2);

        Page<Book> bookPage = new PageImpl<>(List.of(book1, book2), pageable, 0);

        // GIVEN
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        // WHEN
        Page<Book> result = bookService.findAll(pageable);
        // THEN
        assertAll(
                () -> verify(bookRepository, times(1)).findAll(pageable),
                () -> assertEquals(bookPage, result)
        );
    }

    @Test
    void findByIdSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book = new Book("123", "Teste", "Autor", genres1, "Editora");
        book.setIsbn("randomId");

        // GIVEN
        when(bookRepository.findById("randomId")).thenReturn(Optional.of(book));
        // WHEN
        Book result = bookService.findById("randomId");
        // THEN
        assertAll(
                () -> verify(bookRepository, times(1)).findById("randomId"),
                () -> assertEquals(book, result)
        );
    }

    @Test
    void findByIdErrorTest() {
        // GIVEN
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
        // THEN
        assertThrows(ObjectNotFoundException.class, () -> bookService.findById("randomId"));
    }

    @Test
    void findByAuthorSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book1 = new Book("123", "Teste", "Autor", genres1, "Editora");

        ArrayList<String> genres2 = new ArrayList<>(Arrays.asList("Ficção", "Terror"));
        Book book2 = new Book("456", "Teste2", "Autor2", genres2, "Editora2");

        ArrayList<Book> bookArrayList = new ArrayList<>(Arrays.asList(book1, book2));

        // GIVEN
        when(bookRepository.findAllByAuthorContainingIgnoreCase("autor")).thenReturn(bookArrayList);
        // WHEN
        List<Book> result = bookService.findByAuthor("autor");
        // THEN
        assertAll(
                () -> verify(bookRepository, times(1)).findAllByAuthorContainingIgnoreCase("autor"),
                () -> assertEquals(bookArrayList, result)

        );
    }

    @Test
    void findByGenreSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book1 = new Book("123", "Teste", "Autor", genres1, "Editora");

        ArrayList<String> genres2 = new ArrayList<>(Arrays.asList("Ficção", "Terror"));
        Book book2 = new Book("456", "Teste2", "Autor2", genres2, "Editora2");

        ArrayList<Book> bookArrayList = new ArrayList<>(Arrays.asList(book1, book2));

        // GIVEN
        when(bookRepository.findByGenresIgnoreCase("ficção")).thenReturn(bookArrayList);
        // WHEN
        List<Book> result = bookService.findByGenre("ficção");
        // THEN
        assertAll(
                () -> verify(bookRepository, times(1)).findByGenresIgnoreCase("ficção"),
                () -> assertEquals(bookArrayList, result)

        );
    }
}