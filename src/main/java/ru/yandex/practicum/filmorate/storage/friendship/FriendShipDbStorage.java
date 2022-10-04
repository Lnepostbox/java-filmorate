package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FriendShipDbStorage implements FriendshipStorage{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendShipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createFriend(Long id, Long friendId){
        String sqlQuery = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public void deleteFriend(Long id, Long friendId){
        String sqlQuery = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?;";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }
}
