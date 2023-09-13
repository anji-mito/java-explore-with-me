package ru.practicum.stats.hit.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.stats.hit.model.EndpointHit;

import java.util.Objects;

@Component
public class HitMapper {

    @Autowired
    private ModelMapper mapper;

    public EndpointHit toEntity(EndpointHitDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, EndpointHit.class);
    }

    public EndpointHitDto toDto(EndpointHit entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, EndpointHitDto.class);
    }
}