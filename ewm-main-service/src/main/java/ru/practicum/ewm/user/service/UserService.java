package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(NewUserRequest userDto);

    void removeById(long id);

    UserDto getById(long id);

    List<UserDto> getAll(List<Long> ids, Integer from, Integer size);
}
