package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {

    User create(User user);

    User update(User user);

    User readById(Long id);

    List<User> readAll();

}