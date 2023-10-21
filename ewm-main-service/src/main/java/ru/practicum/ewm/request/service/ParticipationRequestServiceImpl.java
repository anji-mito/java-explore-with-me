package ru.practicum.ewm.request.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.model.Status;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestMapper requestMapper;

    public ParticipationRequestServiceImpl(RequestRepository requestRepository, EventRepository eventRepository,
            UserRepository userRepository, ParticipationRequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(long userId) {

        return null;
    }

    @Transactional
    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        if (requestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ConflictException(
                    "Пользователь с id: " + userId + " уже подал заявку на событие с id: " + eventId);
        }
        var requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + userId + " не был найден"));
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не был найден"));
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("Событие с id: " + eventId + " еще не опубликован");
        }
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException(
                    "Пользователь с id: " + userId + " является инициатором событии с id: " + eventId);
        }
        if (event.getParticipantLimit() == event.getConfirmedRequests() && event.getParticipantLimit() != 0) {
            throw new ConflictException("У событии с id: " + eventId + " все места уже заняты");
        }
        if (!event.isRequestModeration()) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        var request = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .requester(requester)
                .status(Status.PENDING)
                .event(event)
                .build();
        if (event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
        }
        return requestMapper.toDto(requestRepository.save(request));
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        var foundRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с id: " + requestId + " не был найден"));
        foundRequest.setStatus(Status.CANCELED);
        return requestMapper.toDto(foundRequest);
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateStatus(long userId, long eventId, EventRequestStatusUpdateRequest dto) {
        var foundEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не было найдено"));
        var foundRequests = requestRepository.findByIdInAndEventId(dto.getRequestIds(), eventId);
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (foundEvent.getParticipantLimit() == 0 || !foundEvent.isRequestModeration()) {
            return result;
        }
        if (dto.getStatus() == Status.CONFIRMED) {
            for (ParticipationRequest request : foundRequests) {
                if (foundEvent.getConfirmedRequests() == foundEvent.getParticipantLimit()) {
                    request.setStatus(Status.REJECTED);
                    result.getRejectedRequests().add(requestMapper.toDto(request));
                }
                if (request.getStatus() != Status.PENDING) {
                    throw new ConflictException("Заявка уже была подтверждена или отменена");
                }
                request.setStatus(dto.getStatus());
                foundEvent.setConfirmedRequests(foundEvent.getConfirmedRequests() + 1);
                result.getConfirmedRequests().add(requestMapper.toDto(request));
            }
        }
        if (dto.getStatus() == Status.REJECTED) {
            for (ParticipationRequest request : foundRequests) {
                if (request.getStatus() == Status.CONFIRMED) {
                    throw new ConflictException("Заявка уже была подтверждена");
                }
                request.setStatus(dto.getStatus());
                result.getRejectedRequests().add(requestMapper.toDto(request));
            }
        }
        return result;
    }

    @Override
    public List<ParticipationRequestDto> getByUserAndEvent(long userId, long eventId) {
        return requestRepository.findByInitiatorIdAndEventId(userId, eventId)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ParticipationRequestDto> getByUser(long userId) {
        return requestRepository.findByRequesterId(userId)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
