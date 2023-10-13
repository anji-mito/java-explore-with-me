package ru.practicum.ewmmainservice.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.service.EventService;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class PublicEventController {
    private final EventService eventService;
    @GetMapping("/{eventId}")
    public EventFullDto getFullEventById(@PathVariable long eventId) {
        return eventService.getById(eventId);
    }
}
