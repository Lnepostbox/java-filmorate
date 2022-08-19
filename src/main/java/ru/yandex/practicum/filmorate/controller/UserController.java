package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j

public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        dataValidation(user);
        user.setId(++id);
        users.put(user.getId(), user);
        log.info(String.format("Пользователь добавлен: id %d, login %s", user.getId(),user.getLogin()));
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException(String.format("Пользователь c id %s не найден", user.getId()));
        }
        dataValidation(user);
        users.put(user.getId(), user);
        log.info(String.format("Данные пользователя обновлены: id %d, login %s", user.getId(),user.getLogin()));
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    private void dataValidation(User user) {

        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

}