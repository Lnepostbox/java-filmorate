package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j

public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private int id = 0;

    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895,12,28);

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        validate(film);
        film.setId(++id);
        films.put(film.getId(), film);
        log.info(String.format("Фильм добавлен: id %d, name %s", film.getId(), film.getName()));
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException(String.format("Фильм c id %s не найден", film.getId()));
        }
        validate(film);
        films.put(film.getId(), film);
        log.info(String.format("Данные фильма обновлены: id %d, name %s", film.getId(), film.getName()));
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            throw new ValidationException("Дата релиза фильма должна быть позже 28 декабря 1895 года");
        }
    }

}
