package com.f1rst.ocatalogodosabio.services;

import com.f1rst.ocatalogodosabio.domain.entities.Book;
import com.f1rst.ocatalogodosabio.domain.entities.User;
import com.f1rst.ocatalogodosabio.repositories.UserRepository;
import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findById(String id) {
        logger.info("[UserService:findById] Searching user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
        logger.info("[UserService:findById] User of id: {} found", id);
        return user;
    }

    public void addBookToLastSeen(String id, Book book) {
        if (id.isBlank()) return;

        User user = findById(id);
        user.addToLastSeen(book);
        userRepository.save(user);
        logger.info("[UserService:addBookToLastSeen] Book of isbn: {} added to user: {} last seen", book.getIsbn(), id);
    }
}
