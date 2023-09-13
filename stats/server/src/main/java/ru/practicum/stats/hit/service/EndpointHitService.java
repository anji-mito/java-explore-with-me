package ru.practicum.stats.hit.service;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;

import java.util.List;

public interface EndpointHitService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(String start, String end, List<String> uris, boolean isUniqueIP);
}
