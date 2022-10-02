package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Film readById(Long id);

    List<Film> readAll();

    List<Film> readPopular(Integer count);

    void createLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);

    boolean checkLike(Long id, Long userId);

}