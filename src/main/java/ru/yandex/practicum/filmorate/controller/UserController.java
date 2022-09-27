package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    protected UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public Optional<User> create(@Valid @RequestBody User user) {
        if (user.getId() > 0) {
            log.info("Пользователь уже существует");
            throw new ValidationException("Не пустой ID");
        }

        Optional<User> optionalUser = userService.create(user);
        log.info("Создана запись пользователя. Количество записей:" + userService.getAll().size());

        return optionalUser;
    }

    @PutMapping()
    public Optional<User> update(@Valid @RequestBody User user) {
        Optional<User> optionalUser = userService.update(user);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        log.info("Обновлена запись пользователя по ID " + user.getId());

        return optionalUser;
    }


    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Integer id) {

        log.info("Выполнен запрос пользователя по ID " + id);

        Optional<User> optionalUser = userService.getById(id);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        return optionalUser;
    }

    @GetMapping()
    public List<User> getAll() {
        log.info("Выполнен запрос всех пользователей");
        return userService.getAll();
    }


    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {

        log.info("Выполнено добавление в друзья к пользователю по ID " + id + ", добавлен пользователь по ID " + friendId);

        Optional<User> optionalUser = userService.getById(id);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        optionalUser = userService.getById(friendId);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {

        log.info("Выполнено удаление из друзей у пользователя по ID " + id + ", удален пользователь по ID " + friendId);

        Optional<User> optionalUser = userService.getById(id);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        optionalUser = userService.getById(friendId);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {

        log.info("Выполнен запрос всех дузей пользователя по ID " + id);
        Optional<User> optionalUser = userService.getById(id);
        optionalUser.orElseThrow(() -> new NotFoundException("Пользователь по ID не найден"));

        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {

        log.info("Выполнен запрос общих дузей пользователя по ID " + id + " для пользователя по ID " + otherId);

        if (userService.getById(id) == null || userService.getById(otherId) == null)
            throw new NotFoundException("Пользователь по ID не найден");

        return userService.getCommonFriends(id, otherId);
    }
}