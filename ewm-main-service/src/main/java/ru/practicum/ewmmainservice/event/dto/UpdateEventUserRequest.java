package ru.practicum.ewmmainservice.event.dto;

import ru.practicum.ewmmainservice.event.model.Location;

public class UpdateEventUserRequest {
    private String annotation;
    private long categoryId;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
