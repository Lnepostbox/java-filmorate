package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@Component
@Validated
@RequestMapping("/films")

public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable(name = "id") @Positive int id) {
        return filmService.getById(id);
    }

    @GetMapping
    public List<Film> getAll() { return filmService.getAll(); }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(name = "count", defaultValue = "10") @Positive int count) {
        return filmService.getPopular(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(
            @PathVariable(name = "id") @Positive int id,
            @PathVariable(name = "userId") @Positive int userId
    ) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(
            @PathVariable(name = "id") @Positive int id,
            @PathVariable(name = "userId") @Positive int userId
    ) {
        return filmService.removeLike(id, userId);
    }
}