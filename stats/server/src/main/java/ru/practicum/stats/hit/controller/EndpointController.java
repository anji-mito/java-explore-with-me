package ru.practicum.stats.hit.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.hit.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@Validated
public class EndpointController {
    private final EndpointHitService endpointHitService;

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> get(
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("get uris={}, unique={}", uris, unique);
        return endpointHitService.getStats(start, end, uris, unique);
    }

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto add(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Endpoint hit added: {}", endpointHitDto);
        return endpointHitService.create(endpointHitDto);
    }
}
