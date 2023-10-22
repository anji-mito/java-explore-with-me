package ru.practicum.ewm.comment.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.model.Comment;

import static ru.practicum.ewm.utility.DateTimeFormatter.API_DATE_TIME_FORMAT;

@Component
public class CommentMapper {
    public Comment toEntity(NewCommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .createdOn(dto.getCreatedOn())
                .text(dto.getText())
                .build();
    }

    public CommentDto toDto(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .createdOn(entity.getCreatedOn().format(API_DATE_TIME_FORMAT))
                .commentatorId(entity.getCommentator().getId())
                .text(entity.getText())
                .isModified(entity.isModified())
                .eventId(entity.getId())
                .build();
    }
}
