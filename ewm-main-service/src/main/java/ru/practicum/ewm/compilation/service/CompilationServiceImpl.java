package ru.practicum.ewm.compilation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto create(NewCompilationDto dto) {
        var compilation = Compilation.builder()
                .title(dto.getTitle())
                .pinned(dto.getPinned())
                .build();
        if (dto.getEvents() != null) {
            var foundEvents = eventRepository.findByIdIn(dto.getEvents());
            compilation.setEvents(foundEvents);
        }
        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void removeById(Long compId) {
        if (compilationRepository.existsById(compId)) {
            compilationRepository.deleteById(compId);
            return;
        }
        throw getNotFoundException(compId);
    }

    @Transactional
    @Override
    public CompilationDto update(long compId, UpdateCompilationRequest dto) {
        var foundComp = findCompilation(compId);
        applyDtoUpdatesToCompilation(dto, foundComp);
        return compilationMapper.toDto(foundComp);
    }

    @Override
    public List<CompilationDto> getAllFilterBy(Boolean pinned, int from, int size) {
        return compilationRepository.findAllFilterBy(pinned, PageRequest.of(from, size))
                .getContent()
                .stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CompilationDto getById(long compId) {
        return compilationMapper.toDto(compilationRepository.findById(compId)
                .orElseThrow(() -> getNotFoundException(compId)));
    }

    private Compilation findCompilation(long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> getNotFoundException(compId));
    }

    private void applyDtoUpdatesToCompilation(UpdateCompilationRequest dto, Compilation compilation) {
        if (dto.getEvents() != null) {
            var foundEvents = eventRepository.findByIdIn(dto.getEvents());
            compilation.setEvents(foundEvents);
        }
        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }
        if (dto.getTitle() != null) {
            compilation.setTitle(dto.getTitle());
        }
    }

    private NotFoundException getNotFoundException(long compId) {
        return new NotFoundException("Подборка событий с id: " + compId + " не была найдена");
    }
}
