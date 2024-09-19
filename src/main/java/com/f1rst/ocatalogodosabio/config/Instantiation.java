package com.f1rst.ocatalogodosabio.config;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.repositories.BookRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Random;

@Configuration
public class Instantiation implements CommandLineRunner {
    @Autowired
    BookRepository bookRepository;

    @Override
    @CacheEvict(value = { "allBooks", "booksByAuthor", "booksByGenre"}, allEntries = true)
    public void run(String... args) {
        Faker faker = new Faker();
        bookRepository.deleteAll();

        for (int i = 0; i < 10000; i++) {
            ArrayList<String> genreList = new ArrayList<>();

            Random random = new Random();

            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                genreList.add(faker.book().genre());
            }

            Book book = new Book(
                    faker.code().isbn13(),
                    faker.book().title(),
                    faker.book().author(),
                    genreList,
                    faker.book().publisher()
            );

            bookRepository.save(book);
        }
        System.out.println("All data was successfully generated");
    }
}
