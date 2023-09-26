package ru.practicum.ewmmainservice.category.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.service.CategoryService;
import ru.practicum.ewmmainservice.exception.BadRequestException;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<CategoryDto> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        if (from > size || from < 0 || size < 1) {
            throw new BadRequestException("Неверно введены данные для параметров запроса size и from");
        }
        return categoryService.getAll(from / size, size);
    }
}
