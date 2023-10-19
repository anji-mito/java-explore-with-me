package ru.practicum.stats.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.stats.exception.BadRequestException;
import ru.practicum.stats.exception.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.practicum.stats.hit.controller.EndpointController.API_DATE_TIME_PATTERN;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationExceptions(BadRequestException e) {
        ApiError.ApiErrorBuilder errorBuilder = ApiError.builder()
                .message(e.getMessage())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toUnmodifiableList()))
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(API_DATE_TIME_PATTERN)));
        if (e.getCause() != null) {
            errorBuilder.reason(e.getCause().toString());
        }
        return errorBuilder.build();
    }
}