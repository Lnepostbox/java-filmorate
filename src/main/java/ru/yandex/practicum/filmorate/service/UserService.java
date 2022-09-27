package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendDao.FriendStorage;
import ru.yandex.practicum.filmorate.storage.userDao.UserStorage;

import java.util.*;

@Service
public class UserService {
    protected UserStorage userStorage;
    protected FriendStorage friendStorage;

    public UserService(UserStorage userStorage,FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public Optional<User> create(User user) {
        return userStorage.create(setNameIfBlank(user));
    }

    public Optional<User> update(User user) { return userStorage.update(setNameIfBlank(user)); }

    public Optional<User> getById(Integer id) {
        return userStorage.getById(id);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public void addFriend(Integer id, Integer idFriend) {
        friendStorage.addFriend(id,idFriend);
    }

    public void removeFriend(Integer id, Integer idFriend) {
        friendStorage.removeFriend(id,idFriend);
    }

    public List<User> getFriends(Integer userId) {
        return friendStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) { return friendStorage.getCommonFriends(userId,otherId); }

    private User setNameIfBlank(User user) {
        if (user.getName().isBlank()) {
            return new User(user.getId(),
                    user.getEmail(),
                    user.getLogin(),
                    user.getLogin(),
                    user.getBirthday());
        }
        return user;
    }
}