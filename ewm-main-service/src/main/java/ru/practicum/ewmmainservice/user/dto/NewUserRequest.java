package ru.practicum.ewmmainservice.user.dto;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class NewUserRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    private String email;
}
