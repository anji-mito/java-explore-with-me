package ru.practicum.ewmmainservice.user.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class NewUserRequest {
    private String name;
    private String email;
}
