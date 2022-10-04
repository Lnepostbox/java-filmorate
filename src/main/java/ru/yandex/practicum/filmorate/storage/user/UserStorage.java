package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {

    User create(User user);

    User update(User user);

    User readById(Long id);

    List<User> readAll();

    List<User> readFriends(Long id);

    List<User> readCommonFriends(Long id, Long otherId);

}