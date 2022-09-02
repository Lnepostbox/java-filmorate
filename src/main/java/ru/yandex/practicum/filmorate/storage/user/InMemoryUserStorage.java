package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component

public class InMemoryUserStorage implements UserStorage{

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User create(User user) {
        validate(user);
        user.setId(++id);
        users.put(user.getId(), user);
        log.info(String.format("Пользователь добавлен: id %d, login %s", user.getId(),user.getLogin()));
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(String.format("Пользователь не найден: id %s", user.getId()));
        }
        validate(user);
        users.put(user.getId(), user);
        log.info(String.format("Данные пользователя обновлены: id %d, login %s", user.getId(),user.getLogin()));
        return user;
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь не найден: id %s", id));
        }
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public void validate(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин пользователя не может содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
