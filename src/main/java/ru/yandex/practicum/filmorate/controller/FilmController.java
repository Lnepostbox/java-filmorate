package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/{filmId}")
    public Film readById(@PathVariable Long filmId) {
        return filmService.readById(filmId);
    }

    @GetMapping
    public List<Film> readAll() {
        return filmService.readAll();
    }

    @GetMapping("/popular")
    public List<Film> readPopular(@RequestParam(required = false) Integer count) {
        return filmService.readPopular(count);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film createLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return filmService.createLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return filmService.deleteLike(filmId, userId);
    }
}