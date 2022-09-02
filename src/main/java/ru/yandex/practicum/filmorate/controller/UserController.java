package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@Component
@Validated
@RequestMapping("/users")

public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable(name = "id") @Positive int id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<User> getAll() { return userService.getAll(); }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(
            @PathVariable(name = "id") @Positive int id,
            @PathVariable(name = "friendId") @Positive int friendId
    ) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(
            @PathVariable(name = "id") @Positive int id,
            @PathVariable(name = "friendId") @Positive int friendId
    ) {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable(name = "id") int id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getFriendIntersection(
            @PathVariable(name = "id") @Positive int id,
            @PathVariable(name = "otherId") @Positive int otherId
    ) {
        return userService.getFriendIntersection(id, otherId);
    }
}