package ru.practicum.stats.hit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.hit.service.EndpointHitService;

import java.util.List;

@Slf4j
@RestController
@Validated
public class EndpointController {
    private final EndpointHitService endpointHitService;

    public EndpointController(EndpointHitService endpointHitService) {
        this.endpointHitService = endpointHitService;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> get(@RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("get start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return endpointHitService.getStats(start, end, uris, unique);
    }

    @PostMapping(path = "/hits")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto add(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Endpoint hit added: {}", endpointHitDto);
        return endpointHitService.create(endpointHitDto);
    }
}
