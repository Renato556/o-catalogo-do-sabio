package com.f1rst.ocatalogodosabio.resources;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.dto.BookDTO;
import com.f1rst.ocatalogodosabio.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookResourceTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookResource bookResource;

    @Test
    void findAllSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book1 = new Book("123", "Teste", "Autor", genres1, "Editora");

        ArrayList<String> genres2 = new ArrayList<>(Arrays.asList("Ficção", "Terror"));
        Book book2 = new Book("456", "Teste2", "Autor2", genres2, "Editora2");

        ArrayList<Book> bookArrayList = new ArrayList<>(Arrays.asList(book1, book2));

        // GIVEN
        when(bookService.findAll()).thenReturn(bookArrayList);
        // WHEN
        ResponseEntity<List<BookDTO>> response = bookResource.findAll();
        // THEN
        verify(bookService, times(1)).findAll();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(bookArrayList.stream().map(BookDTO::new).toList());
    }

    @Test
    void findByIdSuccessTest() {
        ArrayList<String> genres1 = new ArrayList<>(Arrays.asList("Ficção", "Aventura"));
        Book book = new Book("123", "Teste", "Autor", genres1, "Editora");
        book.setId("randomId");

        // GIVEN
        when(bookService.findById("randomId")).thenReturn(book);
        // WHEN
        ResponseEntity<BookDTO> response = bookResource.findById("randomId");
        // THEN
        verify(bookService, times(1)).findById("randomId");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(book);
    }
}