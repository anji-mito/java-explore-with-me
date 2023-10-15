package ru.practicum.ewmmainservice.category.dto;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class NewCategoryDto {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
