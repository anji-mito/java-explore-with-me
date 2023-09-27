package ru.practicum.ewmmainservice.event.dto;

import lombok.*;
import ru.practicum.ewmmainservice.event.model.Location;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class NewEventDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String title;
}
