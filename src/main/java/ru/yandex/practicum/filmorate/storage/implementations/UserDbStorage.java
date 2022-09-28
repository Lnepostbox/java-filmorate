package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserMapper;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        String sqlQuery = "INSERT INTO users (email, login, user_name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"user_id"});

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());

            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(4, Date.valueOf(birthday));
            }
            return statement;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET email = ?, login = ?, user_name = ?, birthday = ? WHERE user_id = ?";

        jdbcTemplate.update(
                sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return findById(user.getId());
    }

    @Override
    public User findById(Long userId) {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users WHERE user_id = ?";

        return jdbcTemplate.query(sqlQuery, new UserMapper(), userId).stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users";

        return jdbcTemplate.query(sqlQuery, new UserMapper());
    }

    public List<User> findFriends(User user) {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friendship WHERE user_id = ?)";

        return jdbcTemplate.query(sqlQuery, new UserMapper(), user.getId());
    }

    @Override
    public List<User> findCommonFriends(User user, User other) {
        String sqlQuery = "SELECT * " +
                "FROM (SELECT user_id, email, login, user_name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friendship WHERE user_id = ?) " +
                "UNION ALL SELECT user_id, email, login, user_name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friendship WHERE user_id = ?) ) " +
                "GROUP BY user_id " +
                "HAVING COUNT(user_id) > 1";

        return jdbcTemplate.query(sqlQuery, new UserMapper(), user.getId(), other.getId());
    }

}