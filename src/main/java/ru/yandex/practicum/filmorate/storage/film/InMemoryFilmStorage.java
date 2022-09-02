package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component

public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Film create(Film film) {
        validate(film);
        film.setId(++id);
        films.put(film.getId(), film);
        log.info(String.format("Фильм добавлен: id %d, name %s", film.getId(), film.getName()));
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(String.format("Фильм не найден: id %s", film.getId()));
        }
        validate(film);
        films.put(film.getId(), film);
        log.info(String.format("Данные фильма обновлены: id %d, name %s", film.getId(), film.getName()));
        return film;
    }

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Фильм не найден: id %s", id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            throw new ValidationException("Дата релиза фильма не может быть ранее 28 декабря 1895 года");
        }
    }
}