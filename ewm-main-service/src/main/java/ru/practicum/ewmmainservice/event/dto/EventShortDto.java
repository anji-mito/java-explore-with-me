package ru.practicum.ewmmainservice.event.dto;

import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;

public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;
}
