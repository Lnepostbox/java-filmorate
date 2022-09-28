package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenreStorage {
    Genre findById(Integer id);
    List<Genre> findAll();
}