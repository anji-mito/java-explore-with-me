package ru.practicum.ewmmainservice.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exception.BadRequestException;
import ru.practicum.ewmmainservice.exception.ConflictException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.exception.model.ApiError;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.API_DATE_TIME_FORMAT;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toUnmodifiableList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(e.getReason())
                .timestamp(LocalDateTime.now().format(API_DATE_TIME_FORMAT))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toUnmodifiableList()))
                .status(HttpStatus.BAD_REQUEST)
                .reason(e.getReason())
                .timestamp(LocalDateTime.now().format(API_DATE_TIME_FORMAT))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toUnmodifiableList()))
                .status(HttpStatus.CONFLICT)
                .reason(e.getReason())
                .timestamp(LocalDateTime.now().format(API_DATE_TIME_FORMAT))
                .build();
    }
    @ExceptionHandler(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIntegrityConstraintViolation(Exception e) {
        return ApiError.builder()
                .message(e.getMessage())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toUnmodifiableList()))
                .status(HttpStatus.CONFLICT)
                .reason(e.getCause().toString())
                .timestamp(LocalDateTime.now().format(API_DATE_TIME_FORMAT))
                .build();
    }
}