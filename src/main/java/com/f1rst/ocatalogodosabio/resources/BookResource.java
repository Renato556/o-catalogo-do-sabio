package com.f1rst.ocatalogodosabio.resources;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.dto.BookDTO;
import com.f1rst.ocatalogodosabio.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        List<Book> bookList = bookService.findAll();
        List<BookDTO> bookDTOList = bookList.stream().map(BookDTO::new).toList();
        return ResponseEntity.ok(bookDTOList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable("id") String id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(new BookDTO(book));
    }
}
