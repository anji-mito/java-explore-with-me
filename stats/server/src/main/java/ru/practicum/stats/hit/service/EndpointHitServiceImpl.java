package ru.practicum.stats.hit.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.hit.mapper.HitMapper;
import ru.practicum.stats.hit.mapper.ViewStatsMapper;
import ru.practicum.stats.hit.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EndpointHitServiceImpl implements EndpointHitService {
    private final HitMapper hitMapper;
    private final EndpointHitRepository endpointHitRepository;
    private final ViewStatsMapper viewStatsMapper;

    public EndpointHitServiceImpl(HitMapper hitMapper, EndpointHitRepository endpointHitRepository,
            ViewStatsMapper viewStatsMapper) {
        this.hitMapper = hitMapper;
        this.endpointHitRepository = endpointHitRepository;
        this.viewStatsMapper = viewStatsMapper;
    }

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        var hitToSave = hitMapper.toEntity(endpointHitDto);
        return hitMapper.toDto(endpointHitRepository.save(hitToSave));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean isUniqueIP) {
        if (isUniqueIP) {
            return endpointHitRepository.findViewStatsUniqueIp(start, end, uris)
                    .stream()
                    .map(viewStatsMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return endpointHitRepository.findViewStats(start, end, uris)
                    .stream()
                    .map(viewStatsMapper::toDto)
                    .collect(Collectors.toList());
        }
    }
}
