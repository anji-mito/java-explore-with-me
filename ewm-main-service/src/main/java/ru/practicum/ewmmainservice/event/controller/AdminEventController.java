package ru.practicum.ewmmainservice.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.State;
import ru.practicum.ewmmainservice.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewmmainservice.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.apiDateTimePattern;

@RestController
@RequestMapping("/admin/events")
@AllArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable long eventId,
            @RequestBody UpdateEventAdminRequest event) {
        return eventService.update(eventId, event);
    }
    @GetMapping
    public List<EventFullDto> search(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = apiDateTimePattern) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = apiDateTimePattern) LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "0") @DefaultValue int from,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return eventService.search(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
