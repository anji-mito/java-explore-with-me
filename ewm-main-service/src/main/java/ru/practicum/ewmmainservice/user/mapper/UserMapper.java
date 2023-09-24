package ru.practicum.ewmmainservice.user.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.user.dto.NewUserRequest;
import ru.practicum.ewmmainservice.user.dto.UserDto;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;
import ru.practicum.ewmmainservice.user.model.User;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
    public UserShortDto toShortDto(User user) {
        return modelMapper.map(user, UserShortDto.class);
    }

    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public User toEntity(NewUserRequest userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
