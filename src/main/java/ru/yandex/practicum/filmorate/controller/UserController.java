package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validate(user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        validate(user);
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User readById(@PathVariable Long id) {
        return userService.readById(id);
    }

    @GetMapping
    public List<User> readAll() {
        return userService.readAll();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User createFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.createFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> readFriends(@PathVariable Long id) {
        return userService.readFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> readCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.readCommonFriends(id, otherId);
    }

    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}