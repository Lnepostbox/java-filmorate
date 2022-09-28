package ru.yandex.practicum.filmorate.serviceTest;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmServiceTests {
    private final FilmService filmService;

    @BeforeEach
    void setUp() {
        createTestFilms();
    }

    @Test
    void shouldThrowExceptionIfNameIsNullOrBlank() {

        Film film3 = new Film(
                3L,
                "",
                "film3 description",
                LocalDate.of(2000, 6, 6),
                110L,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        Film film4 = new Film(
                4L,
                null,
                "film4 description",
                LocalDate.of(2000, 7, 7),
                111,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        assertThrows(ValidationException.class, () -> filmService.validate(film3));
        assertThrows(ValidationException.class, () -> filmService.validate(film4));
    }

    @Test
    void shouldThrowExceptionIfDescriptionIsLonger200() {
        final String tooLongFilmDescription = "film3 description: Marty McFly," +
                "a 17-year-old high school student, is accidentally sent thirty years" +
                "into the past in a time-traveling DeLorean invented by his close friend," +
                "the eccentric scientist Doc Brown";

        // Фильм со слишком длинным описанием
        Film film3 = new Film(
                3L,
                "film3 name",
                tooLongFilmDescription,
                LocalDate.of(2000, 8, 8),
                110,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        assertTrue(film3.getDescription().length() > 200);
        assertThrows(ValidationException.class, () -> filmService.validate(film3));
    }

    @Test
    void shouldThrowExceptionIfReleaseDateIsBefore1895() {
        // Фильм с датой релиза ранее 28.12.1895
        Film film3 = new Film(
                3L,
                "film3 name",
                "film3 description",
                LocalDate.of(1895, 12, 27),
                110,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        assertTrue(film3.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)));
        assertThrows(ValidationException.class, () -> filmService.validate(film3));
    }

    @Test
    void shouldThrowExceptionIfDurationIsNotPositive() {
        // Фильм с нулевой продолжительностью
        Film film3 = new Film(
                3L,
                "film3 name",
                "film3 description",
                LocalDate.of(1986, 6, 6),
                0,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        // Фильм с отрицательной продолжительностью
        Film film4 = new Film(
                4,
                "film4 name",
                "film4 description",
                LocalDate.of(1987, 7, 7),
                -1,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        // Фильм с положительной продолжительностью
        Film film5 = new Film(
                5L,
                "film5 name",
                "film5 description",
                LocalDate.of(1988, 8, 8),
                1,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        assertThrows(ValidationException.class, () -> filmService.validate(film3));
        assertThrows(ValidationException.class, () -> filmService.validate(film4));
        assertDoesNotThrow(() -> filmService.validate(film5));
    }

    void createTestFilms() {
        Film film1 = new Film(
                1,
                "film1 name",
                "film1 description",
                LocalDate.of(1985, 7, 3),
                116,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        Film film2 = new Film(
                2L,
                "film2 name",
                "film2 description",
                LocalDate.of(1988, 1, 7),
                118,
                new Mpa(1, "mpaName"),
                new ArrayList<>()
        );

        filmService.create(film1);
        filmService.create(film2);
    }
}