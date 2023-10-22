package ru.practicum.ewm.comment.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private long id;
    private Long commentatorId;
    private Long eventId;
    private String text;
    private String createdOn;
    private boolean isModified;
}
