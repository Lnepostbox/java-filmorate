package ru.yandex.practicum.filmorate.storage.filmDao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> create(Film film);

    Optional<Film> update(Film film);

    Optional<Film> getById(Integer id);

    List<Film> getAll();

    List<Film> getPopular(Integer count);

}