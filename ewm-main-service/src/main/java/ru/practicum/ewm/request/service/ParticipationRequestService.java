package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDto> getRequestsByUser(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);

    EventRequestStatusUpdateResult updateStatus(long userId, long eventId, EventRequestStatusUpdateRequest dto);

    List<ParticipationRequestDto> getByUserAndEvent(long userId, long eventId);

    List<ParticipationRequestDto> getByUser(long userId);
}
