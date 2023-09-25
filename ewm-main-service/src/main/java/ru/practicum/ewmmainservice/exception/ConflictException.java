package ru.practicum.ewmmainservice.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final String reason;
    public ConflictException(String message) {
        super(message);
        reason = "Ошибка при выполнении запроса";
    }
}
