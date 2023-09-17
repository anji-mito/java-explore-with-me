package ru.practicum.ewmmainservice.category.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String name;
}
