package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User getById(int id) {
        if (id <= 0) {
            throw new NotFoundException("id не может быть меньше либо равно 0");
        }
        return userStorage.getById(id).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User addFriend(int userId, int friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        if (user.equals(friend)) {
            throw new ValidationException("Пользватель не может добавлять в себя друзья");
        }
        if (user.getFriends().contains(friend.getId())) {
            throw new ValidationException("Пользователь уже есть в друзьях");
        }
        user.addFriend(friend);
        friend.addFriend(user);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        if (!user.getFriends().contains(friend.getId())) {
            throw new NotFoundException("Пользователя нет в друзьях");
        }
        user.removeFriend(friend);
        friend.removeFriend(user);
        return user;
    }

    public List<User> getFriends(int id) {
        return getById(id).getFriends().stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

   public List<User> getFriendIntersection(int id, int otherId) {
        return getById(id).getFriends().stream()
                .filter(getById(otherId).getFriends()::contains)
                .map(this::getById)
                .collect(Collectors.toList());
    }

}