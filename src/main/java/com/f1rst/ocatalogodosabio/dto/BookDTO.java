package com.f1rst.ocatalogodosabio.dto;

import com.f1rst.ocatalogodosabio.domain.entities.Book;

import java.io.Serializable;
import java.util.List;

public class BookDTO implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private List<String> genre;
    private String publisher;

    public BookDTO(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.publisher = book.getPublisher();
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

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
