package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Film findById(Long id);

    List<Film> findAll();

    List<Film> findPopular(Long count);
}