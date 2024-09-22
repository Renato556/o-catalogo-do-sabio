package com.f1rst.ocatalogodosabio.repositories;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    Page<Book> findAll(Pageable pageable);

    List<Book> findAllByAuthorContainingIgnoreCase(String author);

    List<Book> findByGenresIgnoreCase(String genre);
}
