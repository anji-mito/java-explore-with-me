package ru.practicum.ewmmainservice.category.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.category.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto create(NewCategoryDto dto) {
        var mappedCategory = categoryMapper.toEntity(dto);
        var addedCategory = categoryRepository.save(mappedCategory);
        return categoryMapper.toDto(addedCategory);
    }

    @Override
    public void removeById(Long id) {
        if(categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        var categoryToUpdateOptional = categoryRepository.findById(id);
        var categoryToUpdate = categoryToUpdateOptional.orElseThrow();
        var name = dto.getName();
        if (name != null && name.length() > 0 && !Objects.equals(name, categoryToUpdate.getName())) {
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
                .orElseThrow();
    }
}
