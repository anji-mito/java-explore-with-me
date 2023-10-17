package ru.practicum.ewmmainservice.category.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.model.Category;

@Component
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category toEntity(NewCategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
