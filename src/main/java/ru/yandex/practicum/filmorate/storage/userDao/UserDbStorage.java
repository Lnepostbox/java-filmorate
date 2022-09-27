package ru.yandex.practicum.filmorate.storage.userDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> create(User user) {
        log.info(user.toString());
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("USER_ID");

        Integer id = simpleJdbcInsert.executeAndReturnKey(mapUser(user)).intValue();
        return getById(id);
    }

    @Override
    public Optional<User> update(User user) {
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where user_id = ?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return getById(user.getId());
    }

    @Override
    public Optional<User> getById(Integer id) {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from users where USER_ID = ?", id);

        if (userRow.next()) {
            log.info("Пользователь с ID {} найден: {}", userRow.getString("USER_ID"), userRow.getString("name"));
            User user = new User(userRow.getInt("USER_ID"),
                    userRow.getString("EMAIL"),
                    userRow.getString("LOGIN"),
                    userRow.getString("NAME"),
                    Objects.requireNonNull(userRow.getDate("BIRTHDAY")).toLocalDate());
            return Optional.of(user);
        } else {
            log.info("Пользователь с ID {} не найден", id);
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser( rs));
    }

    public static User makeUser(ResultSet userRows) throws SQLException {
        return new User(userRows.getInt("USER_ID"),
                userRows.getString("EMAIL"),
                userRows.getString("LOGIN"),
                userRows.getString("NAME"),
                userRows.getDate("BIRTHDAY").toLocalDate());
    }

    private Map<String, Object> mapUser(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }
}
