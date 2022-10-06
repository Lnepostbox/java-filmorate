package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserMapper;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

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

        return user;
    }

    @Override
    public User readById(Long id) {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users WHERE user_id = ?;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> userMapper.mapUser(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id  не найден"));
    }

    @Override
    public List<User> readAll() {
        String sqlQuery = "SELECT user_id, email, login, user_name, birthday FROM users;";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> userMapper.mapUser(rs));
    }

}