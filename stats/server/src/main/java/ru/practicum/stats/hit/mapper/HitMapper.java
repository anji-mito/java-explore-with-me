package ru.practicum.stats.hit.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.stats.hit.model.EndpointHit;

import java.time.LocalDateTime;

import static ru.practicum.stats.hit.service.EndpointHitServiceImpl.DATE_TIME_FORMAT;

@Component
public class HitMapper {

    @Autowired
    private ModelMapper mapper;

    public EndpointHit toEntity(EndpointHitDto dto) {
        return EndpointHit.builder()
                .app(dto.getApp())
                .ip(dto.getIp())
                .uri(dto.getUri())
                .timestamp(LocalDateTime.parse(dto.getTimestamp(), DATE_TIME_FORMAT))
                .build();
    }

    public EndpointHitDto toDto(EndpointHit entity) {
        return EndpointHitDto.builder()
                .app(entity.getApp())
                .ip(entity.getIp())
                .uri(entity.getUri())
                .build();
    }
}