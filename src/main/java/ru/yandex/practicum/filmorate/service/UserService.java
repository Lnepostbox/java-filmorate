package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User create(User user) {
        validate(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        validate(user);
        return userStorage.update(user);
    }

    public User findById(Long id) {
        User user = userStorage.findById(id);
        validate(user);
        return user;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User addFriend(Long userId, Long friendId) {
        if (userId < 1 || friendId < 1) {
            throw new NotFoundException("id не может быть отрицательным");
        }

        User user = findById(userId);
        User friend = findById(friendId);

        friendshipStorage.addFriend(user, friend);
        return user;
    }

    public User removeFriend(Long userId, Long friendId) {
        if (userId < 1 || friendId < 1) {
            throw new NotFoundException("id не может быть отрицательным");
        }

        User user = findById(userId);
        User friend = findById(friendId);

        friendshipStorage.removeFriend(user, friend);
        return user;
    }

    public List<User> findFriends(Long userId) {
        User user = findById(userId);
        return userStorage.findFriends(user);
    }

    public List<User> findCommonFriends(Long userId, Long otherId) {
        User user = findById(userId);
        User other = findById(otherId);
        return userStorage.findCommonFriends(user, other);
    }

    public void validate(User user) {
        if (user == null) {
            log.warn("Попытка получить пользователя по несуществующему id");
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        if (user.getId() < 0) {
            log.warn("Попытка добавить пользователя с отрицательным id ({})", user.getId());
            throw new NotFoundException("id не может быть отрицательным");
        }

        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Попытка добавить пользователя с неверным адресом электронной почтой \"{}\"", user.getEmail());
            throw new ValidationException(
                    "Неверный адрес электронной почты");
        }

        int loginLinesNumber = user.getLogin().split(" ").length;

        if (user.getLogin().isBlank() || loginLinesNumber > 1) {
            log.warn("Попытка добавить пользователя с неверным логином \"{}\"", user.getLogin());
            throw new ValidationException(
                    "Неверный логин");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Попытка добавить пользователя с неверной датой рождения {}", user.getBirthday());
            throw new ValidationException(
                    "Дата рождения указана неверно");
        }

        if (user.getName().isBlank()) {
            log.info("Попытка добавить пользователя с пустым именем. В качестве имени будет установлен логин \"{}\"",
                    user.getLogin());
            user.setName(user.getLogin());
        }
    }
}