package ru.practicum.ewmmainservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmmainservice.event.model.Location;

@AllArgsConstructor
@Getter
@Setter
public class UpdateEventUserRequest {
    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
