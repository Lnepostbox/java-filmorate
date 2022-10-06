package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserMapper;
import java.util.List;

@Repository
public class FriendShipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public FriendShipDbStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
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

    @Override
    public List<User> readFriends(Long id) {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friendship WHERE user_id = ?);";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> userMapper.mapUser(rs), id);
    }

    @Override
    public List<User> readCommonFriends(Long id, Long otherId) {
        String sqlQuery = "SELECT * " +
                "FROM (SELECT user_id, email, login, user_name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friendship WHERE user_id = ?) " +
                "UNION ALL SELECT user_id, email, login, user_name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friendship WHERE user_id = ?) ) " +
                "GROUP BY user_id " +
                "HAVING COUNT(user_id) > 1;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> userMapper.mapUser(rs), id, otherId);
    }

}
