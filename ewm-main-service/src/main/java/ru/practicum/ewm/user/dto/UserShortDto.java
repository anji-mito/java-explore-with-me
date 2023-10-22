package ru.practicum.ewm.user.dto;

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
