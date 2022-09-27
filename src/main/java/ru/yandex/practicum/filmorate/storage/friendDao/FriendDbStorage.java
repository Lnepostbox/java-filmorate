package ru.yandex.practicum.filmorate.storage.friendDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.userDao.UserDbStorage;

import java.util.List;

@Slf4j
@Component
@Primary
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addFriend(Integer userId, Integer friendId) {
        String sqlForCheck = "select USER_ID from friends where USER_ID = ? and FRIEND_ID = ?";

        if (jdbcTemplate.queryForRowSet(sqlForCheck, userId, friendId).next()) {
            throw new ValidationException("Пользавотель уже добавлен в друзья");
        }

        String sql = "insert into friends (user_id, friend_id) values (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
        return true;
    }

    @Override
    public List<User> getFriends(Integer userId) {
        String sql = "select  u.user_id, email, login, name, birthday " +
                "from friends as f " +
                "join users as u on f.friend_id = u.user_id " +
                "where f.user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> UserDbStorage.makeUser(rs), userId);
    }

    @Override
    public boolean removeFriend(Integer userId, Integer friendId) {
        String sqlForCheck = "select USER_ID from friends where USER_ID = ? and FRIEND_ID = ?";
        if (jdbcTemplate.queryForRowSet(sqlForCheck, userId, friendId).next()) {
            String sql = "DELETE FROM friends WHERE USER_ID = ? and FRIEND_ID = ?";
            jdbcTemplate.update(sql, userId, friendId);
        }
        return true;
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        String sql = "select  u.user_id, u.email, u.login, u.name, u.birthday " +
                "from friends as f " +
                "join users as u on f.friend_id = u.user_id " +
                "where f.user_id = ? and f.friend_id IN (" +
                "select friend_id from friends where user_id = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> UserDbStorage.makeUser(rs), userId, otherId);
    }
}