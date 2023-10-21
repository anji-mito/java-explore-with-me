package ru.practicum.ewm.category.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(NewCategoryDto dto) {
        var mappedCategory = categoryMapper.toEntity(dto);
        var addedCategory = categoryRepository.save(mappedCategory);
        return categoryMapper.toDto(addedCategory);
    }

    @Override
    public void removeById(Long id) {
        if (categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        var categoryToUpdate = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с id " + id + " не была найдена"));
        var name = dto.getName();
        if (!Objects.equals(name, categoryToUpdate.getName())) {
            categoryToUpdate.setName(name);
        }
        return categoryMapper.toDto(categoryToUpdate);
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size))
                .map(categoryMapper::toDto)
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CategoryDto getById(long id) {
        return categoryRepository
                .findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Категория с id " + id + " не была найдена"));
    }
}
