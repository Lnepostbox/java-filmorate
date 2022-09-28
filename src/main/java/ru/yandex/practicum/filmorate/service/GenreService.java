package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre findById(Integer id) {
        Genre genre = genreStorage.findById(id);
        validate(genre);
        return genre;
    }

    public List<Genre> findAll() {
        return genreStorage.findAll();
    }

    public void validate(Genre genre) {
        if (genre == null) {
            throw new NotFoundException("Жанр с таким id не найден");
        }

        if (genre.getId() < 0) {
            throw new NotFoundException("id жанра не может быть отрицательным");
        }
    }

}