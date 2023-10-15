package ru.practicum.ewmmainservice.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
