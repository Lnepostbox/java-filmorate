package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/{userId}")
    public User readById(@PathVariable Long userId) {
        return userService.readById(userId);
    }

    @GetMapping
    public List<User> readAll() {
        return userService.readAll();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User createFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return userService.createFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> readFriends(@PathVariable Long userId) {
        return userService.readFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> readCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        return userService.readCommonFriends(userId, otherId);
    }
}