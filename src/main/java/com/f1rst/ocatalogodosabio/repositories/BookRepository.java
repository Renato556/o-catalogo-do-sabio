package com.f1rst.ocatalogodosabio.repositories;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAllByAuthorContainingIgnoreCase(String author);
}
