package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    public ValidationException(HttpStatus badRequest, String message) {
        super(message);
    }
}
