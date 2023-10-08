package ru.practicum.ewmmainservice.request.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.request.mapper.ParticipationRequestMapper;
import ru.practicum.ewmmainservice.request.model.ParticipationRequest;
import ru.practicum.ewmmainservice.request.model.Status;
import ru.practicum.ewmmainservice.request.repository.RequestRepository;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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
        var requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + userId + " не был найден"));
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не был найден"));
        var request = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .requester(requester)
                .status(Status.PENDING)
                .event(event)
                .build();
        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        return null;
    }
}
