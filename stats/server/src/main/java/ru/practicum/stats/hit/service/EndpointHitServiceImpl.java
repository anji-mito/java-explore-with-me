package ru.practicum.stats.hit.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.hit.mapper.HitMapper;
import ru.practicum.stats.hit.mapper.ViewStatsMapper;
import ru.practicum.stats.hit.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EndpointHitServiceImpl implements EndpointHitService {
    private final HitMapper hitMapper;
    private final EndpointHitRepository endpointHitRepository;
    private final ViewStatsMapper viewStatsMapper;

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, boolean isUniqueIP) {
        LocalDateTime startDT = LocalDateTime.parse(start, DATE_TIME_FORMAT);
        LocalDateTime endDT = LocalDateTime.parse(end, DATE_TIME_FORMAT);

        if (isUniqueIP) {
            return endpointHitRepository.findViewStatsUniqueIp(startDT, endDT, uris)
                    .stream()
                    .map(viewStatsMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return endpointHitRepository.findViewStats(startDT, endDT, uris)
                    .stream()
                    .map(viewStatsMapper::toDto)
                    .collect(Collectors.toList());
        }
    }
}
