package com.f1rst.ocatalogodosabio.repositories;

import com.f1rst.ocatalogodosabio.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
