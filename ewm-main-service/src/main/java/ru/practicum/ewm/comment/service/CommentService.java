package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentsByUser(long userId);
    List<CommentDto> getCommentsByEvent(long eventId);
    List<CommentDto> getCommentById(long commentId);
    CommentDto create(long userId, long eventId, NewCommentDto dto);
    CommentDto update(long userId, long commentId, String text);
    void removeByIdByCommentator(long userId, long commentId);
    void removeByIdByAdmin(long commentId);
}
