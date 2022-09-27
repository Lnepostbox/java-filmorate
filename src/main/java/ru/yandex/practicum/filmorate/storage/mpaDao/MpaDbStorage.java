package ru.yandex.practicum.filmorate.storage.mpaDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Primary
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> getAll() {
        String sql = "select * from rating_MPA";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    @Override
    public Optional<MPA> getById(Integer id) {
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from rating_MPA where rating_mpa_id = ?", id);

        if (mpaRows.next()) {
            log.info("Рейтинг MPA с ID {}: {}", mpaRows.getString("rating_mpa_id"), mpaRows.getString("name"));
            MPA mpa = new MPA(mpaRows.getInt("rating_mpa_id"), mpaRows.getString("name"));
            return Optional.of(mpa);
        } else {
            log.info("Рейтинг МРА с ID {}: не найден", id);
            return Optional.empty();
        }
    }

    private MPA makeMpa(ResultSet mpaRows) throws SQLException {
        return new MPA(mpaRows.getInt("rating_mpa_id"), mpaRows.getString("name"));
    }
}
