package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

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
        return userStorage.update(user).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
    }

    public User findById(Long id) {
        return userStorage.findById(id).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User addFriend(Long userId, Long friendId) {
        User user = findById(userId);
        User friend = findById(friendId);
        friendshipStorage.addFriend(user, friend);
        return user;
    }

    public User removeFriend(Long userId, Long friendId) {
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
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}