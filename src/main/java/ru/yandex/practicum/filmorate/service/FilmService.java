package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film getById(int id) {
        if (id <= 0) {
            throw new NotFoundException("id не может быть меньше либо равно 0");
        }
        return filmStorage.getById(id).orElseThrow(() ->
                new NotFoundException("Фильм не найден"));
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public List<Film> getPopular(int count) {
        return filmStorage.getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film addLike(int filmId, int userId) {
        Film film = getById(filmId);
        User user = userService.getById(userId);
        if (film.getLikes().contains(user.getId())) {
            throw new ValidationException("Лайк фильма от пользователя уже есть");
        }
        film.addLike(user);
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        Film film = getById(filmId);
        User user = userService.getById(userId);
        if (!film.getLikes().contains(user.getId())) {
            throw new NotFoundException("Нет лайка фильма от пользователя");
        }
        film.removeLike(user);
        return film;
    }

}