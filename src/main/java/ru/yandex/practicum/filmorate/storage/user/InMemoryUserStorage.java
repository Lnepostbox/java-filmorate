package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Repository

public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User create(User user) {
        user.setId(++id);
        users.put(user.getId(), user);
        log.info(String.format("Пользователь добавлен: id %d, login %s", user.getId(),user.getLogin()));
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        log.info(String.format("Данные пользователя обновлены: id %d, login %s", user.getId(),user.getLogin()));
        return user;
    }

    @Override
    public Optional<User> getById(int id) { return Optional.ofNullable(users.get(id)); }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

}
