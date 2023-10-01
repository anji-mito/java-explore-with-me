package ru.practicum.ewmmainservice.request.service;

import ru.practicum.ewmmainservice.request.dto.ParticipationRequestDto;

import java.util.List;

public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    @Override
    public List<ParticipationRequestDto> getRequestsByUser(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        return null;
    }
}
