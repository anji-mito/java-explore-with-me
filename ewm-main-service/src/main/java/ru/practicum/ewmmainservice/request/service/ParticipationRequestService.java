package ru.practicum.ewmmainservice.request.service;

import ru.practicum.ewmmainservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDto> getRequestsByUser(long userId);
    ParticipationRequestDto createRequest(long userId, long eventId);
    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
