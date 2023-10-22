package ru.practicum.ewm.comment.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utility.DateTimeFormatter.API_DATE_TIME_PATTERN;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class NewCommentDto {
    private long id;
    private String text;
    @DateTimeFormat(pattern = API_DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    private boolean isModified;
}
