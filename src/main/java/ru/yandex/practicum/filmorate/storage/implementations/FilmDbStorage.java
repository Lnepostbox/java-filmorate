package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mappers.FilmMapper;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmMapper filmMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmMapper = filmMapper;
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "INSERT INTO films (film_name, description, release_date, duration, rating) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);

        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films " +
                "SET film_name = ?, description = ?, release_date = ?, duration = ?, rating = ? " +
                "WHERE film_id = ?;";

        jdbcTemplate.update(
                sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );

        return film;
    }

    @Override
    public Film readById(Long id) {
        String sqlQuery = "SELECT film_id, film_name, description, release_date, duration, " +
                "ratings.rating_id, ratings.rating_name " +
                "FROM films JOIN ratings ON films.rating = ratings.rating_id " +
                "WHERE film_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> filmMapper.mapFilm(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Фильм с таким id  не найден"));
    }

    @Override
    public List<Film> readAll() {
        String sqlQuery = "SELECT film_id, film_name, description, release_date, duration, " +
                "ratings.rating_id, ratings.rating_name " +
                "FROM films " +
                "JOIN ratings ON films.rating = ratings.rating_id;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> filmMapper.mapFilm(rs));
    }

    @Override
    public List<Film> readPopular(Integer count) {
        String sqlQuery = "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, " +
                "r.rating_id, r.rating_name " +
                "FROM films AS f " +
                "JOIN ratings AS r ON f.rating = r.rating_id " +
                "LEFT JOIN likes AS l ON f.film_id = l.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> filmMapper.mapFilm(rs), count);
    }

}