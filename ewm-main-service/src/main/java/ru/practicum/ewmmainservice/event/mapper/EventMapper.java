package ru.practicum.ewmmainservice.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.category.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EventMapper(CategoryMapper categoryMapper, UserMapper userMapper) {
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
    }

    public Event toEntity(NewEventDto dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .categories(List.of(Category.builder().id(dto.getCategoryId()).build()))
                .description(dto.getDescription())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMAT))
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
                .categories(event.getCategories().stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(DATE_TIME_FORMAT))
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
                .category(event.getCategories().stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getEventDate().format(DATE_TIME_FORMAT))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DATE_TIME_FORMAT))
                .id(event.getId())
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().format(DATE_TIME_FORMAT))
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
}
