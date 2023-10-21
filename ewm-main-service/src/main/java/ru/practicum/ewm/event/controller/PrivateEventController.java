package ru.practicum.ewm.event.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.BadRequestException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/users")
public class PrivateEventController {
    private final EventService eventService;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(
            @Valid @NotNull @Min(1) @PathVariable Long userId,
            @Valid @RequestBody NewEventDto dto) {
        validateEventDate(dto.getEventDate());
        return eventService.create(userId, dto);
    }

    private void validateEventDate(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException(
                    "дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
        }
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(
            @Valid @Min(1) @PathVariable long userId,
            @Valid @Min(1) @PathVariable long eventId,
            @Valid @RequestBody UpdateEventUserRequest dto) {
        if (dto.getEventDate() != null) {
            validateEventDate(dto.getEventDate());
        }
        return eventService.updateByInitiator(userId, eventId, dto);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getAllByUser(
            @Valid @NotNull @Min(1) @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return eventService.getEventsByUser(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getFullEventById(
            @PathVariable @Valid @Min(1) @NotNull Long userId,
            @PathVariable @Valid @Min(1) @NotNull Long eventId) {
        return eventService.getFullEventById(eventId, userId);
    }

}
