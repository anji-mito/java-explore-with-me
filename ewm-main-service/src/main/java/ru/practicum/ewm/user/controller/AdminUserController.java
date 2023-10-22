package ru.practicum.ewm.user.controller;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/admin/users")
@AllArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid NewUserRequest newUserRequest) {
        return userService.create(newUserRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.removeById(id);
    }

    @GetMapping
    public List<UserDto> getAll(
            @RequestParam(required = false) @NotNull List<Long> ids,
            @RequestParam(required = false, defaultValue = "0") @NotNull Integer from,
            @RequestParam(required = false, defaultValue = "10") @NotNull Integer size) {
        return userService.getAll(ids, from / size, size);
    }
}
