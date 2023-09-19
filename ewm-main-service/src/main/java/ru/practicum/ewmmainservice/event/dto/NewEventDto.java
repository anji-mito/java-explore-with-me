package ru.practicum.ewmmainservice.event.dto;

import ru.practicum.ewmmainservice.event.model.Location;

public class NewEventDto {
    private String annotation;
    private Long categoryId;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String title;
}
