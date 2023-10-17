package ru.practicum.ewm.compilation.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.exception.BadRequestException;

import java.util.List;

@RestController
@Validated
@RequestMapping("/compilations")
@AllArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping("/{id}")
    public CompilationDto getById(@PathVariable long id) {
        return compilationService.getById(id);
    }

    @GetMapping
    public List<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        if (from > size || from < 0 || size < 1) {
            throw new BadRequestException("Неверно введены данные для параметров запроса size и from");
        }
        return compilationService.getAllFilterBy(pinned, from, size);
    }
}
