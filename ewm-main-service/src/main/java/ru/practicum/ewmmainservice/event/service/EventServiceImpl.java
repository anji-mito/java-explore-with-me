package ru.practicum.ewmmainservice.event.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.category.repository.CategoryRepository;
import ru.practicum.ewmmainservice.event.dto.*;
import ru.practicum.ewmmainservice.event.mapper.EventMapper;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.ConflictException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, boolean onlyAvailable, SortEventsBy sort, int from, int size) {
        return eventRepository.FilterBy(text, categories, paid, rangeStart, rangeEnd, State.PUBLISHED,
                        PageRequest.of(from, size))
                .getContent()
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EventFullDto getById(long id) {
        return eventMapper.toDto(eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Событие с id " + id + " не был найден")));
    }

    @Override
    public EventFullDto updateByInitiator(long userId, long eventId, UpdateEventUserRequest dto) {
        var foundEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не был найден"));
        if (foundEvent.getInitiator().getId() != userId) {
            throw new ConflictException("Событие с id: " + eventId + " не пренадлежит пользователю с id: " + userId);
        }
        if (foundEvent.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя редактировать уже подтвержденное событие");
        }
        var event = eventMapper.toEntity(dto);
        if (event.getAnnotation() != null) {
            foundEvent.setAnnotation(event.getAnnotation());
        }
        var category = categoryRepository.findById(dto.getCategory());
        category.ifPresent(foundEvent::setCategory);
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
        if (dto.getStateAction() != null) {
            foundEvent.setState(event.getState());
        }
        if (event.getTitle() != null) {
            foundEvent.setTitle(event.getTitle());
        }
        if (dto.getStateAction() == StateAction.CANCEL_REVIEW) {
            foundEvent.setState(State.CANCELED);
        }
        if (dto.getStateAction() == StateAction.SEND_TO_REVIEW) {
            foundEvent.setState(State.PENDING);
        }
        return eventMapper.toDto(foundEvent);
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
                .orElseThrow(() -> new NotFoundException("Категория с id: " + dto.getCategory() + " не была найдена"));
        eventToAdd.setCreatedOn(LocalDateTime.now());
        eventToAdd.setState(State.PENDING);
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
        if (dto.getStateAction() == StateAction.PUBLISH_EVENT && foundEvent.getState() == State.PUBLISHED) {
            throw new ConflictException("Событие уже опубликовано");
        } else if (dto.getStateAction() == StateAction.REJECT_EVENT && foundEvent.getState() == State.PUBLISHED) {
            throw new ConflictException("Нельзя отклонить уже опубликованое событие");
        } else if (dto.getStateAction() == StateAction.PUBLISH_EVENT && foundEvent.getState() == State.PENDING) {
            foundEvent.setState(State.PUBLISHED);
            foundEvent.setPublishedOn(LocalDateTime.now());
        } else if (dto.getStateAction() == StateAction.CANCEL_REVIEW && foundEvent.getState() != State.PUBLISHED) {
            throw new ConflictException("Нельзя отменить уже опубликованое событие");
        } else if (dto.getStateAction() == StateAction.PUBLISH_EVENT && foundEvent.getState() == State.CANCELED) {
            throw new ConflictException("Нельзя опубликовать уже отмененное событие");
        } else if (dto.getStateAction() == StateAction.REJECT_EVENT && foundEvent.getState() != State.PENDING) {
            foundEvent.setState(State.CANCELED);
        } else if (dto.getStateAction() == StateAction.REJECT_EVENT) {
            foundEvent.setState(State.CANCELED);
        } else if (dto.getStateAction() == StateAction.CANCEL_REVIEW) {
            foundEvent.setState(State.CANCELED);
        } else if (dto.getStateAction() == StateAction.SEND_TO_REVIEW) {
            foundEvent.setState(State.PENDING);
        }
        if (event.getAnnotation() != null) {
            foundEvent.setAnnotation(event.getAnnotation());
        }
        if (dto.getCategory() != 0) {
            var category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(
                            () -> new NotFoundException("Категория с id: " + dto.getCategory() + " не была найдена"));
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
        if (event.getTitle() != null) {
            foundEvent.setTitle(event.getTitle());
        }
        return eventMapper.toDto(foundEvent);
    }

    @Override
    public List<EventFullDto> search(List<Long> users, List<State> states, List<Long> categories,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return eventRepository.FilterBy(users, states, categories, rangeStart, rangeEnd, PageRequest.of(from, size))
                .getContent()
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
