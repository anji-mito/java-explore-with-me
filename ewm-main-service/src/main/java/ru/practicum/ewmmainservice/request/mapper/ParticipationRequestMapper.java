package ru.practicum.ewmmainservice.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.request.model.ParticipationRequest;

import java.time.LocalDateTime;

import static ru.practicum.ewmmainservice.utility.DateTimeFormatter.API_DATE_TIME_FORMAT;

@Component
public class ParticipationRequestMapper {
    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated().format(API_DATE_TIME_FORMAT))
                .status(request.getStatus())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .build();
    }

    public ParticipationRequest toEntity(ParticipationRequestDto dto) {
        return ParticipationRequest.builder()
                .id(dto.getId())
                .created(LocalDateTime.parse(dto.getCreated(), API_DATE_TIME_FORMAT))
                .status(dto.getStatus())
                .build();
    }
}
