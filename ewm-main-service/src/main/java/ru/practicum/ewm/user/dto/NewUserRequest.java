package ru.practicum.ewm.user.dto;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class NewUserRequest {
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    private String email;
}
