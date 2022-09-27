package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.filmDao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.likeDao.LikeStorage;
import ru.yandex.practicum.filmorate.storage.userDao.UserStorage;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoTest {
    private final FilmStorage filmStorage;

    @Test
    public void shouldGetById() {
        int filmId = 1;
        Optional<Film> film1 = filmStorage.getById(filmId);

        assertThat(film1)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("id", filmId));
    }

    @Test
    public void shouldGetAllFilms() {
        assertThat(filmStorage.getAll().size()).isEqualTo(3);
    }



}
