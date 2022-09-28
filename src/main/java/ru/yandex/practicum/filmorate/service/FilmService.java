package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, UserService userService, LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.likeStorage = likeStorage;
    }

    public Film create(Film film) {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        validate(film);
        return filmStorage.update(film).orElseThrow(() ->
                new NotFoundException("Фильм не найден"));
    }

    public Film findById(Long id) {
        return filmStorage.findById(id).orElseThrow(() ->
                new NotFoundException("Фильм не найден"));
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public List<Film> findPopular(Long count) {
        if (count == null || count == 0) {
            count = 10L;
        }
        return filmStorage.findPopular(count);
    }

    public Film addLike(Long filmId, Long userId) {
        Film film = findById(filmId);
        User user = userService.findById(userId);
        likeStorage.addLike(film, user);
        return film;
    }

    public Film removeLike(Long filmId, Long userId) {
        Film film = findById(filmId);
        User user = userService.findById(userId);
        likeStorage.removeLike(film, user);
        return film;

    }

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Попытка добавить фильм с датой релиза раньше 28.12.1895");
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Дата релиза фильма не может быть раньше 28.12.1895");
        }
    }
}