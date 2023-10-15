package ru.practicum.ewmmainservice.event.service;

import ru.practicum.ewmmainservice.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    //PUBLIC
    List<EventShortDto> getEvents(String text, List<Integer> categories, boolean paid,
            String rangeStart, String rangeEnd, boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getById(long id);

    //PRIVATE
    EventFullDto updateByInitiator(long userId, long eventId, UpdateEventUserRequest dto);

    List<EventShortDto> getEventsByUser(long userId, int from, int size);

    EventFullDto create(long userId, NewEventDto dto);

    EventFullDto getFullEventById(long id, long userId);

    //ADMIN
    EventFullDto update(long id, UpdateEventAdminRequest dto);

    List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories,
            String rangeStart, String rangeEnd, int from, int size);

    List<EventFullDto> search(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, int from, int size);
}
