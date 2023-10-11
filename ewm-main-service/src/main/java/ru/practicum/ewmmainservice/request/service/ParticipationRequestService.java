package ru.practicum.ewmmainservice.request.service;

import ru.practicum.ewmmainservice.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewmmainservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmmainservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDto> getRequestsByUser(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
    EventRequestStatusUpdateResult updateStatus(long userId, long eventId, EventRequestStatusUpdateRequest dto);
    List<ParticipationRequestDto> getByUserAndEvent(long userId, long eventId);
}
