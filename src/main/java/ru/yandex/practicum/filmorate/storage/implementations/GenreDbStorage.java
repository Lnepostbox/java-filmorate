package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mappers.GenreMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> findById(Integer genreId) {
        String sqlQuery = "SELECT genre_id, genre_name FROM genres WHERE genre_id = ?";

        return jdbcTemplate.query(sqlQuery, new GenreMapper(), genreId).stream()
                .findAny();
    }

    @Override
    public List<Genre> findAll() {
        String sqlQuery = "SELECT genre_id, genre_name FROM genres";

        return jdbcTemplate.query(sqlQuery, new GenreMapper());
    }
}