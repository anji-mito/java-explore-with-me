package ru.practicum.ewmmainservice.user.service;

import ru.practicum.ewmmainservice.user.dto.NewUserRequest;
import ru.practicum.ewmmainservice.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(NewUserRequest userDto);

    void removeById(long id);

    UserDto getById(long id);

    List<UserDto> getAll(List<Long> ids, Integer from, Integer size);
}
