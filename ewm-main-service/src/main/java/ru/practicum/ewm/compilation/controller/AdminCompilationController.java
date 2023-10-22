package ru.practicum.ewm.compilation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/admin/compilations")
@AllArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PatchMapping("/{compId}")
    public CompilationDto update(
            @Valid @Min(1) @NotNull @PathVariable Long compId,
            @Valid @RequestBody UpdateCompilationRequest dto) {
        return compilationService.update(compId, dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto dto) {
        return compilationService.create(dto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Valid @Min(1) @NotNull @PathVariable Long compId) {
        compilationService.removeById(compId);
    }
}
