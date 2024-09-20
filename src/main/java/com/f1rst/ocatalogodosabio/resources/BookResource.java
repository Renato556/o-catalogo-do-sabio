package com.f1rst.ocatalogodosabio.resources;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.dto.BookDTO;
import com.f1rst.ocatalogodosabio.services.BookService;
import com.f1rst.ocatalogodosabio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookResource {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "100") int size) {
        Page<Book> bookPage = bookService.findAll(PageRequest.of(page, size));
        List<BookDTO> bookDTOList = bookPage.getContent().stream().map(BookDTO::new).toList();
        return ResponseEntity.ok(bookDTOList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable("id") String id,
                                            @RequestHeader(value = "user", defaultValue = "") String userId) {
        Book book = bookService.findById(id);
        userService.addBookToLastSeen(userId, book);
        return ResponseEntity.ok(new BookDTO(book));
    }

    @GetMapping(path = "/author/{author}")
    public ResponseEntity<List<BookDTO>> findByAuthor(@PathVariable("author") String author) {
        List<Book> bookList = bookService.findByAuthor(author);
        List<BookDTO> bookDTOList = bookList.stream().map(BookDTO::new).toList();
        return ResponseEntity.ok(bookDTOList);
    }

    @GetMapping(path = "/genre/{genre}")
    public ResponseEntity<List<BookDTO>> findByGenre(@PathVariable("genre") String genre) {
        List<Book> bookList = bookService.findByGenre(genre);
        List<BookDTO> bookDTOList = bookList.stream().map(BookDTO::new).toList();
        return ResponseEntity.ok(bookDTOList);
    }
}
