package com.f1rst.ocatalogodosabio.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Document
public class Book implements Serializable {
    @Id
    private String isbn;
    private String title;
    private String author;
    private List<String> genres;
    private String publisher;

    public Book() {
    }

    public Book(String isbn, String title, String author, List<String> genres, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getIsbn(), book.getIsbn()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getGenres(), book.getGenres()) && Objects.equals(getPublisher(), book.getPublisher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getAuthor(), getGenres(), getPublisher());
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genres=" + genres +
                ", publisher=" + publisher +
                '}';
    }
}
