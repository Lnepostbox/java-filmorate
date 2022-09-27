package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping()
    public Optional<Film> create(@Valid @RequestBody Film film) {

        if (film.getId() > 0) {
            log.info("Фильм уже существует");
            throw new ValidationException("Не пустой ID");
        }

        log.info("Создана запись фильма. Количество записей:" + filmService.getAll().size());
        return filmService.create(film);
    }

    @PutMapping()
    public Optional<Film> update(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Не задан ID фильма");
        }

        Optional<Film> optionalFilm = filmService.update(film);
        optionalFilm.orElseThrow(() -> new NotFoundException("Фильм по ID не найден"));
        log.info("Обновлена запись фильма по ID " + film.getId());
        return optionalFilm;
    }

    @GetMapping("{id}")
    public Optional<Film> getById(@PathVariable Integer id) {
        log.info("Выполнен запрос фильма по ID " + id);
        Optional<Film> optionalFilm = filmService.getById(id);
        optionalFilm.orElseThrow(() -> new NotFoundException("Фильм по ID не найден"));
        return optionalFilm;
    }

    @GetMapping()
    public List<Film> getAll() {
        log.info("Выполнен запрос всех фильмов");
        return filmService.getAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {

        log.info("Выполнено добавление лайка фильму по ID " + id + " от пользователя по ID " + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Выполнено удаление лайка фильму по ID " + id + " от пользователя по ID " + userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Выполнен запрос получения " + count + " популярных фильмов");
        return filmService.getPopular(count);
    }
}