package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;

@Repository
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createLike(Long id, Long userId) {
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, id, userId);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?;";
        jdbcTemplate.update(sqlQuery, id, userId);
    }

    @Override
    public boolean checkLike(Long id, Long userId) {
        String sqlQuery = "SELECT COUNT(user_id) FROM likes WHERE film_id = ? AND user_id = ?;";
        Integer like = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id, userId);
        return like != null;
    }
}
