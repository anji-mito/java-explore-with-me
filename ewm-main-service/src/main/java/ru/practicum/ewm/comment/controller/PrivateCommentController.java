package ru.practicum.ewm.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("users")
@AllArgsConstructor
public class PrivateCommentController {
    private final CommentService commentService;

    @PatchMapping("/{userId}/comments/{id}")
    public CommentDto update(
            @PathVariable long id,
            @PathVariable long userId,
            @Valid @NotBlank @RequestBody String text) {
        return commentService.update(userId, id, text);
    }

    @PostMapping("/{userId}/events/{eventId}/comments/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @PathVariable long userId,
            @PathVariable long eventId,
            @Valid @RequestBody NewCommentDto dto) {
        return commentService.create(userId, eventId, dto);
    }

    @DeleteMapping("/{userId}/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable long userId,
            @PathVariable long id) {
        commentService.removeByIdByCommentator(userId, id);
    }
}
