package ru.practicum.ewm.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class NewCommentDto {
    @NotBlank
    @Size(max = 7000)
    private String text;
}
