package ru.yandex.practicum.filmorate.storage.interfaces;

public interface LikeStorage {

    void createLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);

    boolean checkLike(Long id, Long userId);

}
