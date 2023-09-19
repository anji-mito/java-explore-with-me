package ru.practicum.ewmmainservice.category.service;

import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto dto);
    void removeById(Long id);
    CategoryDto update(Long id, CategoryDto dto);
    List<CategoryDto> getAll(Integer from, Integer size);
    CategoryDto getById(long id);
}
