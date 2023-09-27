package ru.practicum.ewmmainservice.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class PrivateEventController {
    private final EventService eventService;
    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable long userId, @RequestBody NewEventDto dto) {
        return eventService.create(userId, dto);
    }

    @GetMapping("/events")
    public List<EventShortDto> getAllByUser(
            @PathVariable long userId,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return eventService.getEventsByUser(userId, from / size, size);
    }

    @GetMapping("/{eventId}/events")
    public EventFullDto getFullEventById(
            @PathVariable long userId,
            @PathVariable long eventId) {
        return eventService.getFullEventById(eventId, userId);
    }
}
