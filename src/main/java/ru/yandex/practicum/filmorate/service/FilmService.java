package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        return filmStorage.update(film);
    }

    public Film findById(Long id) {
        Film film = filmStorage.findById(id);
        validate(film);
        return film;
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
        if (film == null) {
            log.warn("Попытка получить фильм по несуществующему id");
            throw new NotFoundException("Фильм с таким id не найден");
        }

        if (film.getId() < 0) {
            log.warn("Попытка добавить фильм с отрицательным id");
            throw new NotFoundException("id фильма не может быть отрицательным");
        }

        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Попытка добавить фильм с пустым названием");
            throw new ValidationException(
                    "Название фильма не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            log.warn("Попытка добавить фильм с описанием более 200 символов");
            throw new ValidationException(
                    "Описание фильма должно быть не более 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Попытка добавить фильм с датой релиза раньше 28.12.1895");
            throw new ValidationException(
                    "Дата релиза фильма должна быть не раньше 28.12.1895");
        }

        if (film.getDuration() <= 0) {
            log.warn("Попытка добавить фильм с нулевой или отрицательной продолжительностью");
            throw new ValidationException(
                    "Продолжительность фильма должна быть положительной");
        }
        if (film.getMpa() == null) {
            log.warn("Попытка добавить фильм без рейтинга MPA");
            throw new ValidationException("Фильм должен содержать рейтинг МРА");
        }
    }
}