package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genge.GenreStorage;
import java.util.List;

@Slf4j
@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService (GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre readById(Integer id) {
        Genre genre = genreStorage.readById(id);
        validate(genre);
        return genre;
    }

    public List<Genre> readAll() {
        return genreStorage.readAll();
    }

    public List<Genre> readByFilmId(Long filmId) {
        return genreStorage.readByFilmId(filmId);
    }

    public void updateForFilm(Long filmId, List<Genre> genres) {
        genreStorage.deleteForFilm(filmId);
        genreStorage.createForFilm(filmId, genres);
    }
    public void validate(Genre genre) {
        if (genre == null) {
            log.warn("Попытка действия с несуществующим id жанра фильма");
            throw new NotFoundException("Жанр фильма с таким id не существует");
        }

        if (genre.getId() < 1) {
            log.warn("Попытка действия с отрицательным id жанра фильма: {}", genre.getId());
            throw new NotFoundException("Жанр фильма не может иметь отрицательный id"); }
    }

}