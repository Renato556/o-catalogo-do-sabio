package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.repositories.BookRepository;
import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final String LOGGER_ID = "[BookService:";

    @Cacheable("allBooks")
    public Page<Book> findAll(Pageable pageable) {
        logger.info("{}findAll] Searching all books page: {}, size: {}", LOGGER_ID, pageable.getPageNumber(), pageable.getPageSize());
        Page<Book> books = bookRepository.findAll(pageable);
        logger.info("{}findAll] Search for page: {}, size: {} was successful", LOGGER_ID, pageable.getPageNumber(), pageable.getPageSize());
        return books;
    }

    public Book findById(String id) {
        logger.info("{}findById] Searching book by id: {}", LOGGER_ID, id);
        Book book = bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No book found with given id"));
        logger.info("{}findById] Search of book with id: {} was successful", LOGGER_ID, id);
        return book;
    }

    @Cacheable("booksByAuthor")
    public List<Book> findByAuthor(String author) {
        logger.info("{}findByAuthor] Searching books of author: {}", LOGGER_ID, author);
        List<Book> books = bookRepository.findAllByAuthorContainingIgnoreCase(author);
        logger.info("{}findByAuthor] Search of book with author: {} was successful", LOGGER_ID, author);
        return books;
    }

    @Cacheable("booksByGenre")
    public List<Book> findByGenre(String genre) {
        logger.info("{}findByGenre] Searching books that contains genre: {}", LOGGER_ID, genre);
        List<Book> books = bookRepository.findByGenresIgnoreCase(genre);
        logger.info("{}findByGenre] Search of book that contains genre: {} was successful", LOGGER_ID, genre);
        return books;
    }
}
