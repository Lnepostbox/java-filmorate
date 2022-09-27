package ru.yandex.practicum.filmorate.storage.likeDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Slf4j
@Component
@Primary
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer idFilm, Integer idUser) {
        findFilm(idFilm);
        findUser(idUser);

        String sqlAddLike = "MERGE INTO likes (FILM_ID, USER_ID) values (?,?)";
        jdbcTemplate.update(sqlAddLike, idFilm, idUser);

        String sqlUpdateCountLike = "UPDATE FILMS f SET f.LIKES_COUNTER = (select count(l.USER_ID) from LIKES l where l.FILM_ID = ?) WHERE f.FILM_ID = ?";
        jdbcTemplate.update(sqlUpdateCountLike, idFilm, idFilm);
    }

    @Override
    public void removeLike(Integer idFilm, Integer idUser) {
        findFilm(idFilm);
        findUser(idUser);

        String sqlAddLike = "DELETE FROM likes WHERE FILM_ID = ? and  USER_ID = ?";
        jdbcTemplate.update(sqlAddLike, idFilm, idUser);

        String sqlUpdateCountLike = "UPDATE FILMS f SET f.LIKES_COUNTER = (select count(l.USER_ID) from LIKES l where l.FILM_ID = ?) WHERE f.FILM_ID = ?";
        jdbcTemplate.update(sqlUpdateCountLike, idFilm, idFilm);
    }

    private void findUser(Integer idUser) {
        String sql = "select * from USERS where USER_ID = ?";
        if(!jdbcTemplate.queryForRowSet(sql,idUser).next()) {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    private void findFilm(Integer idFilm) {
        String sql = "select * from FILMS where FILM_ID = ?";
        if(!jdbcTemplate.queryForRowSet(sql,idFilm).next()) {
            throw new NotFoundException("Фильм не найден");
        }
    }
}

