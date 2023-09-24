package ru.practicum.ewmmainservice.event.dto;

import lombok.*;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.event.model.Location;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class EventFullDto {
    private String annotation;
    private List<CategoryDto> category;
    private long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private long views;
}
