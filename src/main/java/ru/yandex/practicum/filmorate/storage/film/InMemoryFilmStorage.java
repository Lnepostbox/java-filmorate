package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Repository

public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Film create(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);
        log.info(String.format("Фильм добавлен: id %d, name %s", film.getId(), film.getName()));
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        log.info(String.format("Данные фильма обновлены: id %d, name %s", film.getId(), film.getName()));
        return film;
    }

    @Override
    public Optional<Film> getById(int id) { return Optional.ofNullable(films.get(id)); }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

}