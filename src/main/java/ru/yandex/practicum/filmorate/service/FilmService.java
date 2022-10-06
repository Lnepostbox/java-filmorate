package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;

import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikeStorage likeStorage, UserService userService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userService = userService;
        this.genreService = genreService;
    }

    public Film create(Film film) {
        Film createdFilm = filmStorage.create(film);
        if (film.getGenres() != null) {
            genreService.updateForFilm(createdFilm.getId(), film.getGenres());
        }
        return createdFilm;
    }

    public Film update(Film film) {
        validate(film);
        if (film.getGenres() != null) {
            genreService.updateForFilm(film.getId(), film.getGenres());
        }
        filmStorage.update(film);
        return readById(film.getId());
    }

    public Film readById(Long id) {
        Film film = filmStorage.readById(id);
        film.getGenres().addAll(genreService.readByFilmId(id));
        validate(film);
        return film;
    }

    public List<Film> readAll() {
        List<Film> films = filmStorage.readAll();
        for (Film film: films) {
            film.getGenres().addAll(genreService.readByFilmId(film.getId()));
        }
        return films;
    }

    public List<Film> readPopular(Integer count) {
        List<Film> films = filmStorage.readPopular(count);
        for (Film film: films) {
            film.getGenres().addAll(genreService.readByFilmId(film.getId()));
        }
        return films;
    }

    public Film createLike(Long filmId, Long userId) {
        Film film = readById(filmId);
        User user = userService.readById(userId);
        likeStorage.createLike(film.getId(), user.getId());
        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        Film film = readById(filmId);
        User user = userService.readById(userId);
        if (!likeStorage.checkLike(filmId, userId)) {
            throw new NotFoundException("Лайк пользователя фильму не найден");
        }
        likeStorage.deleteLike(film.getId(), user.getId());
        return film;

    }

    public void validate(Film film) {
        if (film == null) {
            log.warn("Попытка получить фильм по несуществующему id");
            throw new NotFoundException("Фильм с таким id не найден");
        }

        if (film.getId() < 1) {
            log.warn("Попытка добавить фильм с отрицательным id: {}", film.getId());
            throw new NotFoundException("Фильм не может иметь отрицательный id");
        }
    }
}