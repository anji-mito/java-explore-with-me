package ru.practicum.ewmmainservice.user.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.user.dto.NewUserRequest;
import ru.practicum.ewmmainservice.user.dto.UserDto;
import ru.practicum.ewmmainservice.user.mapper.UserMapper;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(NewUserRequest userDto) {
        var userEntity = userMapper.toEntity(userDto);
        var addedUser = userRepository.save(userEntity);
        return userMapper.toDto(addedUser);
    }

    @Override
    public void removeById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getById(long id) {
        return userRepository
                .findById(id)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<UserDto> getAll(List<Long> ids, Integer from, Integer size) {
        if (ids == null) {
            return userRepository.findAll(PageRequest.of(from, size))
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());
        } else {
            return userRepository.findAllByIdIn(ids, PageRequest.of(from, size))
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());
        }

    }
}
