package com.f1rst.ocatalogodosabio.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document
public class User implements Serializable {
    @Id
    private String id;
    private String name;

    @DBRef
    private List<Book> lastSeen = new ArrayList<>();

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getLastSeen() {
        return lastSeen;
    }

    public void addToLastSeen(Book book) {
        this.lastSeen.add(book);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastSeen=" + lastSeen +
                '}';
    }
}
