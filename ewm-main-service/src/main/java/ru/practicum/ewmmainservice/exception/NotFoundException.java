package ru.practicum.ewmmainservice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String reason;
    public NotFoundException(String message) {
        super(message);
        reason = "Объект не был найден";
    }
}