package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final FilmService filmService;
    private final UserService userService;

    @Test
    public void shouldFindFilmById() {
        long filmId = 1L;
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.findById(filmId));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("id", filmId));
    }

    @Test
    public void shouldCreateAndFindAllFilms() {
        Film film4 = new Film(
                4L,
                "film4",
                "description4",
                LocalDate.of(2000, 4, 14),
                2000,
                new Mpa(1, "G"),
                new ArrayList<>()
        );

        int filmsCountBefore = filmStorage.findAll().size();

        filmStorage.create(film4);

        int filmsCountAfter = filmStorage.findAll().size();

        assertThat(filmsCountAfter).isEqualTo(filmsCountBefore + 1);
        assertThat(filmStorage.findAll()).contains(film4);
    }

    @Test
    public void shouldUpdateFilm() {
        Film film2New = new Film(
                2L,
                "film2new",
                "description2new",
                LocalDate.of(2000, 2, 12),
                2000,
                new Mpa(1, "G"),
                new ArrayList<>()
        );

        int filmsCountBefore = filmStorage.findAll().size();

        filmStorage.update(film2New);

        String filmNewDescription = filmStorage.findById(film2New.getId()).getDescription();
        int filmsCountAfter = filmStorage.findAll().size();

        assertThat(filmNewDescription).isEqualTo("description2new");
        assertThat(filmsCountBefore).isEqualTo(filmsCountAfter);
    }

    @Test
    public void shouldFindPopularFilms() {
        Film film1 = filmService.findById(1L);
        Film film2 = filmService.findById(2L);
        Film film3 = filmService.findById(3L);
        User user1 = userService.findById(1L);
        User user2 = userService.findById(2L);
        User user3 = userService.findById(3L);
        final int mostPopularFilmIndex = 0;
        long filmsCount = 10L;

        likeStorage.addLike(film2, user1);
        assertThat(filmStorage.findPopular(filmsCount).get(mostPopularFilmIndex).getId()).isEqualTo(film2.getId());

        likeStorage.addLike(film3, user1);
        likeStorage.addLike(film3, user2);
        assertThat(filmStorage.findPopular(filmsCount).get(mostPopularFilmIndex).getId()).isEqualTo(film3.getId());

        likeStorage.addLike(film1, user1);
        likeStorage.addLike(film1, user2);
        likeStorage.addLike(film1, user3);
        assertThat(filmStorage.findPopular(filmsCount).get(0).getId()).isEqualTo(film1.getId());

        likeStorage.removeLike(film1, user3);
        likeStorage.removeLike(film1, user2);
        assertThat(filmStorage.findPopular(filmsCount).get(mostPopularFilmIndex).getId()).isEqualTo(film3.getId());

        filmsCount = 2L;
        List<Film> twoMostPopularFilms = filmStorage.findPopular(filmsCount);

        assertThat(twoMostPopularFilms.size()).isEqualTo(filmsCount);

        filmsCount = 1L;
        List<Film> oneMostPopularFilm = filmStorage.findPopular(filmsCount);

        assertThat(oneMostPopularFilm.size()).isEqualTo(filmsCount);

    }
}
