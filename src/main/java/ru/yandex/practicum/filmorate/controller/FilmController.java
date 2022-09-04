package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);
    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validate(film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        validate(film);
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable(name = "id") int id) {
        return filmService.getById(id);
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(name = "count", defaultValue = "10") @Positive int count) {
        return filmService.getPopular(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "userId") int userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "userId") int userId) {
        return filmService.removeLike(id, userId);
    }

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            throw new ValidationException("Дата релиза фильма не может быть ранее 28 декабря 1895 года");
        }
    }
}