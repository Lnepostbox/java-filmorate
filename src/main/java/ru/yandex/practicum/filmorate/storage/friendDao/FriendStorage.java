package ru.yandex.practicum.filmorate.storage.friendDao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

    boolean addFriend(Integer userId, Integer friendId);

    boolean removeFriend(Integer userId, Integer friendId);

    List<User> getFriends(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer otherId);
}