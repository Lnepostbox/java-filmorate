/*
package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 0;

    @Override
    public Optional<Film>  create(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);
        log.info(String.format("Фильм добавлен: id %d, name %s", film.getId(), film.getName()));
        return Optional.of(film);
    }

    @Override
    public Optional<Film> update(Film film) {
        films.put(film.getId(), film);
        log.info(String.format("Данные фильма обновлены: id %d, name %s", film.getId(), film.getName()));
        return Optional.of(film);
    }

    @Override
    public Optional<Film> getById(Integer id) { return Optional.ofNullable(films.get(id)); }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getPopular(Integer count) {
        return getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

}*/
