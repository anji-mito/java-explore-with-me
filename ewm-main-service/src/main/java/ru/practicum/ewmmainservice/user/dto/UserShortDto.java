package ru.practicum.ewmmainservice.user.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserShortDto {
    private long id;
    private String name;
}
