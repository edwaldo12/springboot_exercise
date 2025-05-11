package com.example.demo.controller;
import com.example.demo.application.usecase.UserMapper;
import com.example.demo.application.usecase.UserService;
import com.example.demo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody UserRequest request) {
        User user = userMapper.toEntity(request);
        User savedUser = userService.createUser(user);
        return userMapper.toResponse(savedUser);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return userMapper.toResponse(user);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAllUsers().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @RequestBody UserRequest request) {
        User userUpdate = userMapper.toEntity(request);
        User updatedUser = userService.updateUser(id, userUpdate);
        return userMapper.toResponse(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
