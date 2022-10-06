package ru.yandex.practicum.filmorate.storage.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage {

    void createFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);

    List<User> readFriends(Long id);

    List<User> readCommonFriends(Long id, Long otherId);
}
