package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.event.model.Location;

import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utility.DateTimeFormatter.API_DATE_TIME_PATTERN;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class UpdateEventUserRequest extends UpdateEventRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private long category;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = API_DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
