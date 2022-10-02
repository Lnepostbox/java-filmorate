package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user); }

    public User update(User user) {
        validate(user);
        return userStorage.update(user); }

    public User readById(Long id) {
        User user = userStorage.readById(id);
        validate(user);
        return user;
    }

    public List<User> readAll() {
        return userStorage.readAll();
    }

    public User createFriend(Long id, Long friendId) {
        User user = readById(id);
        User friend = readById(friendId);
        if (user.equals(friend)) {
            throw new ValidationException("Пользватель не может добавлять в себя друзья");
        }
        userStorage.createFriend(user.getId(), friend.getId());
        return user;
    }

    public User deleteFriend(Long id, Long friendId) {
        User user = readById(id);
        User friend = readById(friendId);
        if (user.equals(friend)) {
            throw new ValidationException("Пользватель не может удалить себя из друзья");
        }
        userStorage.deleteFriend(user.getId(), friend.getId());
        return user;
    }

    public List<User> readFriends(Long id) {
        User user = readById(id);
        return userStorage.readFriends(user.getId());
    }

    public List<User> readCommonFriends(Long id, Long otherId) {
        User user = readById(id);
        User other = readById(otherId);
        return userStorage.readCommonFriends(user.getId(), other.getId());
    }

    public void validate(User user) {
        if (user == null) {
            log.warn("Попытка получить пользователя по несуществующему id");
            throw new NotFoundException("Фильм с таким id не найден");
        }

        if (user.getId() < 1) {
            log.warn("Попытка добавить пользователя с отрицательным id: {}", user.getId());
            throw new NotFoundException("Пользователь не может иметь отрицательный id");
        }
    }
}