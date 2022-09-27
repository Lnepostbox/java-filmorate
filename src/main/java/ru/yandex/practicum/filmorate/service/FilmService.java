package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.filmDao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.likeDao.LikeStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage storage;
    private final LikeStorage likeStorage;

    public FilmService(FilmStorage storage, LikeStorage likeStorage) {
        this.storage = storage;
        this.likeStorage = likeStorage;
    }

    public Optional<Film> create(Film film) {
        return storage.create(film);
    }

    public Optional<Film> update(Film film) {
        return storage.update(film);
    }

    public Optional<Film> getById(Integer id) {
        return storage.getById(id);
    }

    public List<Film> getAll() {
        return storage.getAll();
    }

    public void addLike(Integer filmId, Integer userId) { likeStorage.addLike(filmId, userId); }

    public void removeLike(Integer filmId, Integer userId) { likeStorage.removeLike(filmId, userId); }

    public List<Film> getPopular(Integer num) {
        return storage.getPopular(num);
    }
}