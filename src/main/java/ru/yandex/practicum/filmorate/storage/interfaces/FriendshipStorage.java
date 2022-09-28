package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

public interface FriendshipStorage {
    void addFriend(User user, User friend);

    void removeFriend(User user, User friend);
}