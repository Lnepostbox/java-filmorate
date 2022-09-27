package ru.yandex.practicum.filmorate.storage.genreDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Primary
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        String sql = "select * from genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public Optional<Genre> getById(Integer id) {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genres where genre_id = ?", id);

        if (genreRows.next()) {
            log.info("Жанр с ID {}: {}", genreRows.getString("genre_id"), genreRows.getString("name"));
            Genre mpa = new Genre(genreRows.getInt("genre_id"), genreRows.getString("name"));
            return Optional.of(mpa);
        } else {
            log.info("Жанр с ID {}: не найден", id);
            return Optional.empty();
        }
    }

    private Genre makeGenre(ResultSet genreRows) throws SQLException {
        return new Genre(genreRows.getInt("genre_id"), genreRows.getString("name"));
    }
}