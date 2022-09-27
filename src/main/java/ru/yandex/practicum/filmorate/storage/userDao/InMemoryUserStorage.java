/*package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    @Override
    public Optional<User> create(User user) {
        user.setId(++id);
        users.put(user.getId(), user);
        log.info(String.format("Пользователь добавлен: id %d, login %s", user.getId(),user.getLogin()));
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User user) {
        users.put(user.getId(), user);
        log.info(String.format("Данные пользователя обновлены: id %d, login %s", user.getId(),user.getLogin()));
        return Optional.of(user);
    }

    @Override
    public Optional<User> getById(Integer id) { return Optional.ofNullable(users.get(id)); }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

}*/
