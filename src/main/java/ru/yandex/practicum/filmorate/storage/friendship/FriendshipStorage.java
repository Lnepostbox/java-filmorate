package ru.yandex.practicum.filmorate.storage.friendship;

public interface FriendshipStorage {

    void createFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);
}
