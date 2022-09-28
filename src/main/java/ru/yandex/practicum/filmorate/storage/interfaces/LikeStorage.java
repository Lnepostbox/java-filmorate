package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface LikeStorage {
    void addLike(Film film, User user);

    void removeLike(Film film, User user);
}