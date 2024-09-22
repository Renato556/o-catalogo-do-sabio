package com.f1rst.ocatalogodosabio.resources.exceptions;

import com.f1rst.ocatalogodosabio.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Method argument type mismatch",
                exception.getMessage(),
                request.getRequestURI()
        );

        logger.error("{}methodArgumentTypeMismatch] {}", LOGGER_ID, exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<StandardError> noResourceFound(NoResourceFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Resource not found",
                exception.getMessage(),
                request.getRequestURI()
        );

        logger.error("{}noResourceFound] {}", LOGGER_ID, exception.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> unexpectedException(Exception exception, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error",
                exception.getMessage(),
                request.getRequestURI()
        );

        logger.error("{}unexpectedException] {}", LOGGER_ID, exception.getMessage());

        return ResponseEntity.internalServerError().body(error);
    }
}
