package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.event.model.Location;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static ru.practicum.ewm.utility.DateTimeFormatter.API_DATE_TIME_PATTERN;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonFormat(pattern = API_DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
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
