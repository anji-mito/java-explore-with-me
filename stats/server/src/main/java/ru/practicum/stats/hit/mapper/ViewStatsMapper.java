package ru.practicum.stats.hit.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.hit.model.ViewStats;

import java.util.Objects;

@Component

public class ViewStatsMapper {
    @Autowired
    private ModelMapper mapper;

    public ViewStats toEntity(ViewStatsDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, ViewStats.class);
    }

    public ViewStatsDto toDto(ViewStats entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, ViewStatsDto.class);
    }

}
