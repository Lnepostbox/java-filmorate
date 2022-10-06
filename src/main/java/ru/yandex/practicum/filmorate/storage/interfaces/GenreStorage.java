package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenreStorage {
    Genre readById(Integer id);

    List<Genre> readAll();

    List<Genre> readByFilmId(Long filmId);

    void createForFilm(Long filmId, List<Genre> genre);

    void deleteForFilm(Long filmId);
}