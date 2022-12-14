package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mappers.GenreMapper;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate, GenreMapper genreMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreMapper = genreMapper;
    }

    @Override
    public Genre readById(Integer id) {
        String sqlQuery = "SELECT genre_id, genre_name FROM genres WHERE genre_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> genreMapper.mapGenre(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Жанр фильма с таким id  не найден"));
    }

    @Override
    public List<Genre> readAll() {
        String sqlQuery = "SELECT genre_id, genre_name FROM genres;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> genreMapper.mapGenre(rs));
    }

    @Override
    public List<Genre> readByFilmId(Long filmId) throws NotFoundException {
        String sqlQuery = "SELECT g.genre_id, g.genre_name " +
                "FROM film_genres AS fg " +
                "JOIN genres AS g " +
                "ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> genreMapper.mapGenre(rs), filmId);
    }

    @Override
    public void createForFilm(Long filmId, List<Genre> genres) {
        List<Genre> genresDistinct = genres
                .stream()
                .distinct()
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(
                "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?);",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement statement, int i) throws SQLException {
                        statement.setLong(1, filmId);
                        statement.setLong(2, genresDistinct.get(i).getId());
                    }
                    public int getBatchSize() {
                        return genresDistinct.size();
                    }
                }
        );
    }

    @Override
    public void deleteForFilm(Long filmId) {
        String sqlQuery = "DELETE FROM film_genres WHERE film_id = ?;";
        jdbcTemplate.update(sqlQuery, filmId);
    }

}