package ru.practicum.ewmmainservice.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.SortEventsBy;
import ru.practicum.ewmmainservice.event.service.EventService;
import ru.practicum.ewmmainservice.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.apiDateTimePattern;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsFilterBy(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = apiDateTimePattern) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = apiDateTimePattern) LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortEventsBy sort,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size) {
        if (text != null && text.length() < 2) {
            throw new BadRequestException("Нужно больше символов для поиска");
        }
        return eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getFullEventById(@PathVariable long id) {
        return eventService.getById(id);
    }
}
