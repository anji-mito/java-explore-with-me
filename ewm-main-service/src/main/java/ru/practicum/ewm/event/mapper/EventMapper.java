package ru.practicum.ewm.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utility.DateTimeFormatter.API_DATE_TIME_FORMAT;

@Component
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public EventMapper(CategoryMapper categoryMapper, UserMapper userMapper) {
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
    }

    public Event toEntity(NewEventDto dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .location(dto.getLocation())
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .build();
    }

    public EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(API_DATE_TIME_FORMAT))
                .id(event.getId())
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public EventFullDto toDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getEventDate().format(API_DATE_TIME_FORMAT))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(API_DATE_TIME_FORMAT))
                .id(event.getId())
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(null)
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public Event toEntity(UpdateEventAdminRequest dto) {
        return Event.builder()
                .annotation(dto.getAnnotation() != null ? dto.getAnnotation() : null)
                .category(dto.getCategory() != 0 ? Category.builder().id(dto.getCategory()).build() : null)
                .description(dto.getDescription() != null ? dto.getDescription() : null)
                .eventDate(dto.getEventDate())
                .location(dto.getLocation() != null ? dto.getLocation() : null)
                .paid(dto.getPaid() != null ? dto.getPaid() : false)
                .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : 0)
                .requestModeration(dto.getRequestModeration() != null ? dto.getRequestModeration() : false)
                .title(dto.getTitle() != null ? dto.getTitle() : null)
                .build();
    }

    public Event toEntity(UpdateEventUserRequest dto) {
        return Event.builder()
                .annotation(dto.getAnnotation() != null ? dto.getAnnotation() : null)
                .category(dto.getCategory() != 0 ? Category.builder().id(dto.getCategory()).build() : null)
                .description(dto.getDescription() != null ? dto.getDescription() : null)
                .eventDate(dto.getEventDate())
                .location(dto.getLocation() != null ? dto.getLocation() : null)
                .paid(dto.getPaid() != null ? dto.getPaid() : false)
                .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : 0)
                .requestModeration(dto.getRequestModeration() != null ? dto.getRequestModeration() : false)
                .title(dto.getTitle() != null ? dto.getTitle() : null)
                .build();
    }
}
