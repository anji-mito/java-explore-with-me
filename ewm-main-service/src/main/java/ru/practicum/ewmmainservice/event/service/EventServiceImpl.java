package ru.practicum.ewmmainservice.event.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.category.repository.CategoryRepository;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewmmainservice.event.mapper.EventMapper;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
            CategoryRepository categoryRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
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
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from, size))
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    @Override
    public EventFullDto create(long userId, NewEventDto dto) {
        var initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не был найден"));
        var eventToAdd = eventMapper.toEntity(dto);
        var category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new NotFoundException("Категория с id: " + dto.getCategory() + " не была найдена")
                );
        eventToAdd.setInitiator(initiator);
        eventToAdd.setCategory(category);
        return eventMapper.toDto(eventRepository.save(eventToAdd));
    }

    @Override
    public EventFullDto getFullEventById(long id, long userId) {
        return eventMapper.toDto(eventRepository.findByIdAndInitiatorId(id, userId)
                .orElseThrow(() -> new NotFoundException("Событие с id " + id + " не был найден")));
    }

    @Transactional
    @Override
    public EventFullDto update(long eventId, UpdateEventAdminRequest dto) {
        var foundEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не был найден"));
        var event = eventMapper.toEntity(dto);
        if (event.getAnnotation() != null) {
            foundEvent.setAnnotation(event.getAnnotation());
        }
        if(dto.getCategory()!=0) {
            var category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория с id: " + dto.getCategory() + " не была найдена"));
            foundEvent.setCategory(category);
        }
        if (event.getDescription() != null) {
            foundEvent.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            foundEvent.setEventDate(event.getEventDate());
        }
        if (event.getLocation() != null) {
            foundEvent.setLocation(event.getLocation());
        }
        if (dto.getPaid() != null) {
            foundEvent.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            foundEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            foundEvent.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getStateAction()!=null) {
            foundEvent.setState(event.getState());
        }
        if(event.getTitle() != null) {
            foundEvent.setTitle(event.getTitle());
        }
        return eventMapper.toDto(foundEvent);
    }

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories,
            String rangeStart, String rangeEnd, int from, int size) {
        return null;
    }
}
