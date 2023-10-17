package ru.practicum.ewmmainservice.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.State;
import ru.practicum.ewmmainservice.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewmmainservice.event.service.EventService;
import ru.practicum.ewmmainservice.exception.BadRequestException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.API_DATE_TIME_FORMAT;
import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.apiDateTimePattern;

@RestController
@Validated
@RequestMapping("/admin/events")
@AllArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventAdminRequest event) {
        if (event.getEventDate() != null) {
            var eventDate = LocalDateTime.parse(event.getEventDate(), API_DATE_TIME_FORMAT);
            if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new BadRequestException(
                        "дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
            }
        }
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
