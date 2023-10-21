package ru.practicum.ewm.compilation.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CompilationMapper {
    private final EventMapper eventMapper;

    public CompilationDto toDto(Compilation entity) {
        List<EventShortDto> eventDtos = entity.getEvents() != null ?
                entity.getEvents()
                        .stream()
                        .map(eventMapper::toShortDto)
                        .collect(Collectors.toUnmodifiableList()) : Collections.emptyList();
        return CompilationDto.builder()
                .title(entity.getTitle())
                .pinned(entity.getPinned())
                .events(eventDtos)
                .id(entity.getId())
                .build();
    }
}
