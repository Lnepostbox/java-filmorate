package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        String sqlQuery = "INSERT INTO users (email, login, user_name, birthday) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET email = ?, login = ?, user_name = ?, birthday = ? WHERE user_id = ?;";

        jdbcTemplate.update(
                sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return readById(user.getId());
    }

    @Override
    public User readById(Long id) {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users WHERE user_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapUser(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id  не найден"));
    }

    @Override
    public List<User> readAll() {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapUser(rs));
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

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapUser(rs), id);
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

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapUser(rs), id, otherId);
    }

    private User mapUser(ResultSet rs) throws SQLException {
        long id = rs.getLong("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("user_name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday);
    }



}