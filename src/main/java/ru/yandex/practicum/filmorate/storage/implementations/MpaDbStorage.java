package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;
import ru.yandex.practicum.filmorate.storage.mappers.MpaMapper;
import java.util.List;

@Repository
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaMapper mpaMapper;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate, MpaMapper mpaMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaMapper = mpaMapper;
    }

    @Override
    public Mpa readById(Integer id) {
        String sqlQuery = "SELECT rating_id, rating_name FROM ratings WHERE rating_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaMapper.mapMpa(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Рейтинг МРА с таким id  не найден"));
    }

    @Override
    public List<Mpa> readAll() {
        String sqlQuery = "SELECT rating_id, rating_name FROM ratings;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaMapper.mapMpa(rs));
    }

}
