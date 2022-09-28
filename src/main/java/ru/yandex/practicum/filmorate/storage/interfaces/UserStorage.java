package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {

    User create(User user);

    User update(User user);

    User findById(Long id);

    List<User> findAll();

    List<User> findFriends(User user);

    List<User> findCommonFriends(User user, User other);
}