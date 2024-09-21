package com.f1rst.ocatalogodosabio.resources.exceptions;

import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);
    private final String LOGGER_ID = "[ResourceExceptionHandler:";

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Object not found",
                exception.getMessage(),
                request.getRequestURI()
        );

        logger.warn("{}objectNotFound] {}", LOGGER_ID, exception.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> unexpectedException(Exception exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Unexpected error",
                exception.getMessage(),
                request.getRequestURI()
        );

        logger.error("{}unexpectedException] {}", LOGGER_ID, exception.getMessage());

        return ResponseEntity.status(status).body(error);
    }
}
