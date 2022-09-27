package ru.yandex.practicum.filmorate.storage.genreDao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    List<Genre> getAll();

    Optional<Genre> getById(Integer id);

}
