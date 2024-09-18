package com.f1rst.ocatalogodosabio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OCatalogoDoSabioApplication {

    public static void main(String[] args) {
        SpringApplication.run(OCatalogoDoSabioApplication.class, args);
    }
}
