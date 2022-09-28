package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film create(Film film);

    Optional<Film> update(Film film);

    Optional<Film> findById(Long id);

    List<Film> findAll();

    List<Film> findPopular(Long count);
}