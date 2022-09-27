package ru.yandex.practicum.filmorate.storage.userDao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    Optional<User> create(User user);

    Optional<User> update(User user);

    Optional<User> getById(Integer id);

    List<User> getAll();

}