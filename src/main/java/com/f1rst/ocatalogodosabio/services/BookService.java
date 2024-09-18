package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.repositories.BookRepository;
import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Cacheable("allBooks")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No book found with given id"));
    }

    @Cacheable("booksByAuthor")
    public List<Book> findByAuthor(String author) {
        return bookRepository.findAllByAuthorContainingIgnoreCase(author);
    }

    @Cacheable("booksByGenre")
    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }
}
