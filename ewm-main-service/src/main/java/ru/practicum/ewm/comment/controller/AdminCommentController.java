package ru.practicum.ewm.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.service.CommentService;

@RestController
@Validated
@RequestMapping("admin")
@AllArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        commentService.removeByIdByAdmin(id);
    }
}
