package ru.practicum.ewmmainservice.category.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.service.CategoryService;
import ru.practicum.ewmmainservice.exception.BadRequestException;

@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PatchMapping("/{id}")
    public CategoryDto update(@PathVariable long id, @RequestBody CategoryDto categoryDto) {
        validateName(categoryDto.getName());
        return categoryService.update(id, categoryDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody NewCategoryDto categoryDto) {
        validateName(categoryDto.getName());
        return categoryService.create(categoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.removeById(id);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new BadRequestException("Поле name не может быть пустым");
        }
        if (name.length() > 50) {
            throw new BadRequestException("Поле name не может содержать больше 50 символов");
        }
        if (name.length() < 2) {
            throw new BadRequestException("Поле name не может содержать меньше, чем 1 символ");
        }
    }
}
