package ru.practicum.ewm.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceIml implements CommentService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getCommentsByUser(long userId) {
        return commentRepository.findAllByCommentatorId(userId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<CommentDto> getCommentsByEvent(long eventId) {
        return commentRepository.findAllByEventId(eventId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CommentDto getCommentById(long commentId) {
        return commentMapper.toDto(getComment(commentId));
    }

    @Override
    public CommentDto create(long userId, long eventId, NewCommentDto dto) {
        var commentator = getUser(userId);
        var event = getEvent(eventId);
        var commentToAdd = commentMapper.toEntity(dto);
        commentToAdd.setModified(false);
        commentToAdd.setCreatedOn(LocalDateTime.now());
        commentToAdd.setCommentator(commentator);
        commentToAdd.setEvent(event);
        return commentMapper.toDto(commentRepository.save(commentToAdd));
    }

    @Transactional
    @Override
    public CommentDto update(long userId, long commentId, String text) {
        Comment comment = getComment(commentId);
        if (userId != comment.getCommentator().getId()) {
            throw new ConflictException("Нельзя редактировать чужой комментарий");
        }
        if (!comment.getText().equals(text)) {
            comment.setText(text);
            comment.setModified(true);
        }
        return commentMapper.toDto(comment);
    }

    @Override
    public void removeByIdByCommentator(long userId, long commentId) {
        var foundComment = getComment(commentId);
        if (userId != foundComment.getCommentator().getId()) {
            throw new ConflictException("Нельзя удалить чужой комментарий");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void removeByIdByAdmin(long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id + " + eventId + " не было найдено"));
    }

    private Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id + " + commentId + " не был найден"));
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id + " + userId + " не был найден"));
    }
}
