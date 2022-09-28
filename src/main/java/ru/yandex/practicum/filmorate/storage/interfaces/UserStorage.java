package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User create(User user);

    Optional<User> update(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    List<User> findFriends(User user);

    List<User> findCommonFriends(User user, User other);
}