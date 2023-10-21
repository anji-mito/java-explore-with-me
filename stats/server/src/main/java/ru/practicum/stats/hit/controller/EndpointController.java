package ru.practicum.stats.hit.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.stats.exception.BadRequestException;
import ru.practicum.stats.hit.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class EndpointController {
    public static final String API_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final EndpointHitService endpointHitService;

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> get(
            @RequestParam @DateTimeFormat(pattern = API_DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = API_DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("get uris={}, unique={}", uris, unique);
        if (start.isAfter(end) || end == null) {
            throw new BadRequestException("Запрос составлен неверно");
        }
        return endpointHitService.getStats(start, end, uris, unique);
    }

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto add(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Endpoint hit added: {}", endpointHitDto);
        return endpointHitService.create(endpointHitDto);
    }
}
