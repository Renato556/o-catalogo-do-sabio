package com.f1rst.ocatalogodosabio.dto;

import com.f1rst.ocatalogodosabio.domain.entities.User;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {
    private String id;
    private String name;
    private List<BookDTO> lastSeen;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
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

    public List<BookDTO> getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(List<BookDTO> lastSeenList) {
        this.lastSeen = lastSeenList;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastSeen=" + lastSeen +
                '}';
    }
}
