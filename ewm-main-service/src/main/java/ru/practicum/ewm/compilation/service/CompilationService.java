package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto dto);
    void removeById(Long compId);
    CompilationDto update(long compId, UpdateCompilationRequest dto);
    List<CompilationDto> getAllFilterBy(Boolean pinned, int from, int size);
    CompilationDto getById(long compId);
}
