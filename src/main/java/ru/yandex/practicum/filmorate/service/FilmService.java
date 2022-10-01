package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreService = genreService;
    }

    public Film create(Film film) {
        validate(film);
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
        return filmStorage.update(film);
    }

    public Film readById(Long id) {
        Film film = filmStorage.readById(id);
        validate(film);
        return film;
    }

    public List<Film> readAll() {
        return filmStorage.readAll();
    }

    public List<Film> readPopular(Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }
        return filmStorage.readPopular(count);
    }

    public Film createLike(Long filmId, Long userId) {
        Film film = readById(filmId);
        User user = userService.readById(userId);
        if (filmStorage.checkLike(filmId, userId)) {
            throw new NotFoundException("Лайк пользователя фильму уже сторит");
        }
        filmStorage.createLike(film.getId(), user.getId());
        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        Film film = readById(filmId);
        User user = userService.readById(userId);
        if (!filmStorage.checkLike(filmId, userId)) {
            throw new NotFoundException("Лайк пользователя фильму не найден");
        }
        filmStorage.deleteLike(film.getId(), user.getId());
        return film;

    }

    public void validate(Film film) {
        if (film == null) {
            log.warn("Попытка получить фильм по несуществующему id");
            throw new NotFoundException("Фильм с таким id не найден");
        }

        if (film.getId() < 0) {
            log.warn("Попытка добавить фильм с отрицательным id: {}", film.getId());
            throw new NotFoundException("Фильм не может иметь отрицательный id");
        }

        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Попытка добавить фильм с пустым названием");
            throw new ValidationException("Фильм не может иметь пустое название");
        }

        if (film.getDescription().length() > 200) {
            log.warn("Попытка добавить фильм с описанием более 200 символов: {}", film.getDescription().length());
            throw new ValidationException("Фильм не может иметь описание более 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Попытка добавить фильм с датой релиза ранее 28.12.1895: {}", film.getReleaseDate());
            throw new ValidationException("Фильм не может иметь дату релиза ранее 28.12.1895");
        }

        if (film.getDuration() <= 0) {
            log.warn("Попытка добавить фильм с нулевой или отрицательной продолжительностью: {}", film.getDuration());
            throw new ValidationException("Фильм не может иметь нулевую или отрицатеьную продолжительность");
        }
        if (film.getMpa() == null) {
            log.warn("Попытка добавить фильм без рейтинга MPA");
            throw new ValidationException("Фильм должен содержать рейтинг МРА");
        }
    }
}