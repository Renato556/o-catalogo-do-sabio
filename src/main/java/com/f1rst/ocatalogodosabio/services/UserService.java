package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.domain.entities.User;
import com.f1rst.ocatalogodosabio.repositories.UserRepository;
import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public void addBookToLastSeen(String id, Book book) {
        if (id.isBlank()) return;

        User user = findById(id);
        user.addToLastSeen(book);
        userRepository.save(user);
    }
}
