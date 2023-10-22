package ru.practicum.stats.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    String reason;

    public BadRequestException(String message) {
        super(message);
        reason = "Ошибка при составлении запроса";
    }
}
