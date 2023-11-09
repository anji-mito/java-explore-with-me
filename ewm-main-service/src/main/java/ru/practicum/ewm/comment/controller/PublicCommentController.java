package ru.practicum.ewm.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public CommentDto getById(@PathVariable long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/users/{userId}")
    public List<CommentDto> getAllByUser(@PathVariable long userId) {
        return commentService.getCommentsByUser(userId);
    }

    @GetMapping("/events/{eventId}")
    public List<CommentDto> getAllByEvent(@PathVariable long eventId) {
        return commentService.getCommentsByEvent(eventId);
    }
}
