package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.time.LocalDate;
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
        validate(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        validate(user);
        return userStorage.update(user);
    }

    public User readById(Long id) {
        User user = userStorage.readById(id);
        validate(user);
        return user;
    }

    public List<User> readAll() {
        return userStorage.readAll();
    }

    public User createFriend(Long userId, Long friendId) {
        User user = readById(userId);
        User friend = readById(friendId);
        userStorage.createFriend(user.getId(), friend.getId());
        return user;
    }

    public User deleteFriend(Long userId, Long friendId) {
        User user = readById(userId);
        User friend = readById(friendId);
        userStorage.deleteFriend(user.getId(), friend.getId());
        return user;
    }

    public List<User> readFriends(Long userId) {
        User user = readById(userId);
        return userStorage.readFriends(user.getId());
    }

    public List<User> readCommonFriends(Long userId, Long otherId) {
        User user = readById(userId);
        User other = readById(otherId);
        return userStorage.readCommonFriends(user.getId(), other.getId());
    }

    public void validate(User user) {
        if (user == null) {
            log.warn("Попытка действия с несуществующим id пользователя");
            throw new NotFoundException("Пользователь с таким id не существует");
        }

        if (user.getId() < 0) {
            log.warn("Попытка действия отрицательный id пользователя: {}", user.getId());
            throw new NotFoundException("Пользователь не может иметь отрицательный id");
        }

        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Попытка добавить пользователя с недопустимым адресом электронной почты: {}",user.getEmail());
            throw new ValidationException("Недопустимый адрес электронной почты пользователя");
        }

        int loginLinesNumber = user.getLogin().split(" ").length;

        if (user.getLogin().isBlank() || loginLinesNumber > 1) {
            log.warn("Попытка добавить пользователя с недопустимым логином: {}", user.getLogin());
            throw new ValidationException("Недопустимый логин пользователя");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Попытка добавить пользователя с недопустимой датой рождения: {}", user.getBirthday());
            throw new ValidationException("Недопустимая дата рождения пользователя");
        }

        if (user.getName().isBlank()) {
            log.info("Попытка добавить пользователя с пустым именем. Вместо имени установлен логин: {}", user.getLogin());
            user.setName(user.getLogin());
        }
    }
}