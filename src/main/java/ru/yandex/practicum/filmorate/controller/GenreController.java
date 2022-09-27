package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping()
    public List<Genre> getAll() {
        log.info("Получение всех жанров");
        return genreService.getAll();
    }

    @GetMapping("{id}")
    public Optional<Genre> getById(@PathVariable Integer id) {
        log.info("Получение жанра по ID " + id);
        Optional<Genre> optionalGenre = genreService.getById(id);
        optionalGenre.orElseThrow(() -> new NotFoundException("Жанр по ID не найден"));
        return optionalGenre;
    }

}
