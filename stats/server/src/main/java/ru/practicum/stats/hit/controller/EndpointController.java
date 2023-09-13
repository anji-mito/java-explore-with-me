package ru.practicum.stats.hit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.hit.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
public class EndpointController {
    private final EndpointHitService endpointHitService;

    public EndpointController(EndpointHitService endpointHitService) {
        this.endpointHitService = endpointHitService;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> get(@RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        return endpointHitService.getStats(start, end, uris, unique);
    }

    @PostMapping(path = "/hits")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto add(@RequestBody EndpointHitDto endpointHitDto) {
        return endpointHitService.create(endpointHitDto);
    }
}
