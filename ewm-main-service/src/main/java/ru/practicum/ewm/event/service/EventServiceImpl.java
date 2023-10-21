package ru.practicum.ewm.event.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.client.hit.EndpointControllerClient;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utility.DateTimeFormatter.API_DATE_TIME_FORMAT;

@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final EndpointControllerClient hitClient;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, boolean onlyAvailable, SortEventsBy sort, int from, int size) {
        try {
            var hit = EndpointHitDto.builder()
                    .app("ewm-main-service")
                    .uri("/events")
                    .ip("192.168.1.111")
                    .timestamp(LocalDateTime.now().format(API_DATE_TIME_FORMAT))
                    .build();
            hitClient.createHit(hit);
            List<String> uris = List.of("/events");
        } catch (Exception e) {
            log.error("Сервис статистики недоступен, не удалось обновить данные о просмотрах");
        }
        return eventRepository.filterBy(text, categories, paid, rangeStart, rangeEnd, State.PUBLISHED,
                        PageRequest.of(from, size))
                .getContent()
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EventFullDto getById(long id) {
        var foundEvent = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Событие с id " + id + " не был найден"));
        try {
            var hit = EndpointHitDto.builder()
                    .app("ewm-main-service")
                    .uri("/events/" + id)
                    .ip("192.168.1.111")
                    .timestamp(LocalDateTime.now().format(API_DATE_TIME_FORMAT))
                    .build();
            hitClient.createHit(hit);
            List<String> uris = List.of("/events/" + id);
            List<ViewStatsDto> statsDto
                    = hitClient.getStats(LocalDateTime.now().minusYears(5), LocalDateTime.now().plusYears(10), uris, true);
            foundEvent.setViews(statsDto.get(0).getHits());
        } catch (Exception e) {
            log.error("Сервис статистики недоступен, не удалось обновить данные о просмотрах");
        }
        return eventMapper.toDto(foundEvent);
    }

    @Override
    public EventFullDto updateByInitiator(long userId, long eventId, UpdateEventUserRequest dto) {
        var foundEvent = getEvent(eventId);
        verifyEventOwnership(userId, foundEvent);
        assertEventIsNotPublished(foundEvent);

        var updatedEvent = applyUpdatesToEvent(foundEvent, dto);

        return eventMapper.toDto(updatedEvent);
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
        var foundEvent = findEventById(eventId);
        var event = eventMapper.toEntity(dto);
        if (dto.getStateAction() != null) {
            processStateAction(foundEvent, dto.getStateAction());
        }
        updateEventFromDto(foundEvent, event, dto);

        return eventMapper.toDto(foundEvent);
    }

    @Override
    public List<EventFullDto> search(List<Long> users, List<State> states, List<Long> categories,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return eventRepository.filterBy(users, states, categories, rangeStart, rangeEnd, PageRequest.of(from, size))
                .getContent()
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не был найден"));
    }

    private void verifyEventOwnership(long userId, Event foundEvent) {
        if (foundEvent.getInitiator().getId() != userId) {
            throw new ConflictException(
                    "Событие с id: " + foundEvent.getId() + " не пренадлежит пользователю с id: " + userId);
        }
    }

    private void assertEventIsNotPublished(Event foundEvent) {
        if (foundEvent.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя редактировать уже подтвержденное событие");
        }
    }

    private Event applyUpdatesToEvent(Event foundEvent, UpdateEventUserRequest dto) {
        var event = eventMapper.toEntity(dto);
        if (event.getAnnotation() != null) foundEvent.setAnnotation(event.getAnnotation());
        categoryRepository.findById(dto.getCategory()).ifPresent(foundEvent::setCategory);
        if (event.getDescription() != null) foundEvent.setDescription(event.getDescription());
        if (event.getEventDate() != null) foundEvent.setEventDate(event.getEventDate());
        if (event.getLocation() != null) foundEvent.setLocation(event.getLocation());
        if (dto.getPaid() != null) foundEvent.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) foundEvent.setParticipantLimit(event.getParticipantLimit());
        if (dto.getRequestModeration() != null) foundEvent.setRequestModeration(dto.getRequestModeration());
        if (dto.getStateAction() != null) foundEvent.setState(event.getState());
        if (event.getTitle() != null) foundEvent.setTitle(event.getTitle());

        updateEventState(dto, foundEvent);
        return foundEvent;
    }

    private void updateEventState(UpdateEventUserRequest dto, Event foundEvent) {
        if (dto.getStateAction() == StateAction.CANCEL_REVIEW) {
            foundEvent.setState(State.CANCELED);
        }
        if (dto.getStateAction() == StateAction.SEND_TO_REVIEW) {
            foundEvent.setState(State.PENDING);
        }
    }

    private Event findEventById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + id + " не был найден"));
    }

    private void processStateAction(Event foundEvent, StateAction action) {
        State currentState = foundEvent.getState();
        switch (action) {
            case PUBLISH_EVENT:
                if (currentState == State.PUBLISHED)
                    throw new ConflictException("Событие уже опубликовано");
                if (currentState == State.CANCELED)
                    throw new ConflictException("Нельзя опубликовать уже отмененное событие");
                if (currentState == State.PENDING) {
                    foundEvent.setState(State.PUBLISHED);
                    foundEvent.setPublishedOn(LocalDateTime.now());
                }
                break;
            case REJECT_EVENT:
                if (currentState == State.PUBLISHED)
                    throw new ConflictException("Нельзя отклонить уже опубликованое событие");
                foundEvent.setState(State.CANCELED);
                break;
            case CANCEL_REVIEW:
                if (currentState != State.PUBLISHED)
                    throw new ConflictException("Нельзя отменить уже опубликованое событие");
                foundEvent.setState(State.CANCELED);
                break;
            case SEND_TO_REVIEW:
                foundEvent.setState(State.PENDING);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный StateAction " + action);
        }
    }

    private void updateEventFromDto(Event foundEvent, Event event, UpdateEventAdminRequest dto) {
        if (event.getAnnotation() != null) foundEvent.setAnnotation(event.getAnnotation());
        if (dto.getCategory() != 0) foundEvent.setCategory(findCategoryById(dto.getCategory()));
        if (event.getDescription() != null) foundEvent.setDescription(event.getDescription());
        if (event.getEventDate() != null) foundEvent.setEventDate(event.getEventDate());
        if (event.getLocation() != null) foundEvent.setLocation(event.getLocation());
        if (dto.getPaid() != null) foundEvent.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) foundEvent.setParticipantLimit(event.getParticipantLimit());
        if (dto.getRequestModeration() != null) foundEvent.setRequestModeration(dto.getRequestModeration());
        if (event.getTitle() != null) foundEvent.setTitle(event.getTitle());
    }

    private Category findCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с id: " + id + " не была найдена"));
    }

}
