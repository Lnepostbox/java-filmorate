package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa readById(Integer id) {
        String sqlQuery = "SELECT rating_id, rating_name FROM ratings WHERE rating_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapMpa(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Рейтинг МРА с таким id  не найден"));
    }
    @Override
    public List<Mpa> readAll() {
        String sqlQuery = "SELECT rating_id, rating_name FROM ratings;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapMpa(rs));
    }

    private Mpa mapMpa(ResultSet rs) throws SQLException {
        int id = rs.getInt("rating_id");
        String name = rs.getString("rating_name");
        return new Mpa(id, name);
    }
}
