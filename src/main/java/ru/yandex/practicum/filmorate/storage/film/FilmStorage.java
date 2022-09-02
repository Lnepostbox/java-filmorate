package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;

public interface FilmStorage {

    LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);

    Film create(Film film);

    Film update(Film film);

    Film getById(int id);

    List<Film> getAll();

    void validate(Film film);
}