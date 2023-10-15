package ru.practicum.ewmmainservice.event.dto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewmmainservice.event.model.Location;
import javax.validation.constraints.*;

import java.time.LocalDateTime;

import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.apiDateTimePattern;

@Getter
@Setter
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
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
