package ru.practicum.ewmmainservice.event.service;

import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.mapper.EventMapper;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.util.List;

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, boolean paid, String rangeStart,
            String rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getById(long id) {
        return eventMapper.toDto(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие с id " + id + " не был найден")));
    }

    @Override
    public List<EventShortDto> getEventsByUser(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto create(long userId, NewEventDto dto) {
        if(userRepository.existsById(userId))
        {
            var mappedEvent = eventMapper.toEntity(dto);
            return eventMapper.toDto(eventRepository.save(mappedEvent));
        }
        throw new NotFoundException("Пользователь с id " + userId + " не был найден");
    }

    @Override
    public EventFullDto getFullEventById(long id, long userId) {
        return null;
    }

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories,
            String rangeStart, String rangeEnd, int from, int size) {
        return null;
    }
}
