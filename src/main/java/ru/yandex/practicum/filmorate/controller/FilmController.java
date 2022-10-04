package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@RequestMapping("/films")
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
    public Film readById(@PathVariable Long id) {
        return filmService.readById(id);
    }

    @GetMapping
    public List<Film> readAll() {
        return filmService.readAll();
    }

    @GetMapping("/popular")
    public List<Film> readPopular(@RequestParam(name = "count", defaultValue = "10") @Positive Integer count) {
        return filmService.readPopular(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film createLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.createLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            log.warn("Попытка добавить фильм с датой релиза ранее 28.12.1895: {}", film.getReleaseDate());
            throw new ValidationException("Фильм не может иметь дату релиза ранее 28.12.1895");
        }
    }
}
