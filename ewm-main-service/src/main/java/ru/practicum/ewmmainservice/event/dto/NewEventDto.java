package ru.practicum.ewmmainservice.event.dto;

import lombok.*;
import ru.practicum.ewmmainservice.event.model.Location;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NewEventDto {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private Location location;
    private boolean paid = false;
    private int participantLimit = 0;
    private boolean requestModeration = true;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
