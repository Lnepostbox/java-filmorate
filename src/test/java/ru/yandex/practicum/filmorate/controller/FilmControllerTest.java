package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FilmControllerTest {
    private final FilmController controller = new FilmController();

    @Test
    void addFilm() {
        Film film = new Film();
        film.setName("Название фильма");
        film.setDescription("Описание фильма");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        controller.add(film);

        assertFalse(controller.getAll().isEmpty());
        assertEquals("Название фильма",controller.getAll().get(0).getName());
    }

    @Test
    void addNullNameFilm() {
        Film film = new Film();
        film.setName(null);
        film.setDescription("Описание фильма");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.add(film)
        );
        assertEquals("Название фильма не может быть пустым",exception.getMessage());
    }

    @Test
    void addBlankNameFilm() {
        Film film = new Film();
        film.setName(" ");
        film.setDescription("Описание фильма");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.add(film)
        );
        assertEquals("Название фильма не может быть пустым",exception.getMessage());
    }

    @Test
    void addLonger200CharacterDescriptionFilm() {
        Film film = new Film();
        film.setName("Название фильма");
        film.setDescription("123456789 123456789 123456789 123456789 123456789 " +
                "123456789 123456789 123456789 123456789 123456789 " +
                "123456789 123456789 123456789 123456789 123456789 " +
                "123456789 123456789 123456789 123456789 123456789 " +
                "1");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.add(film)
        );
        assertEquals("Описание фильма не должно привышать 200 символов",exception.getMessage());
    }

    @Test
    void addEarlier1895y12m28dDateFilm() {
        Film film = new Film();
        film.setName("Название фильма");
        film.setDescription("Описание фильма");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(1895,12,27));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.add(film)
        );
        assertEquals("Дата релиза фильма должна быть позже 28 декабря 1895 года",exception.getMessage());
    }

    @Test
    void addNegativeDurationFilm() {
        Film film = new Film();
        film.setName("Название фильма");
        film.setDescription("Описание фильма");
        film.setDuration(-200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.add(film)
        );
        assertEquals("продолжительность фильма должна быть положительной",exception.getMessage());
    }

    @Test
    void updateFilm() {
        Film film = new Film();
        film.setName("Название фильма");
        film.setDescription("Описание фильма");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        controller.add(film);

        Film filmNew = new Film();
        filmNew.setId(1);
        filmNew.setName("Название фильма новое");
        filmNew.setDescription("Описание фильма");
        filmNew.setDuration(200);
        filmNew.setReleaseDate(LocalDate.of(2020,2,20));

        controller.update(filmNew);

        assertNotEquals(film, controller.getAll().get(0));
        assertEquals("Название фильма новое", controller.getAll().get(0).getName());
    }

    @Test
    void updateNotExistingFilm() {
        Film film = new Film();
        film.setName("Название фильма");
        film.setDescription("Описание фильма");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(2020,2,20));

        controller.add(film);
        film.setId(100);

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.update(film)
        );
        assertEquals("Фильм c id 100 не найден", exception.getMessage());
    }

    @Test
    void getAllFilms() {
        Film film1 = new Film();
        film1.setName("Название фильма 1");
        film1.setDescription("Описание фильма 1");
        film1.setDuration(200);
        film1.setReleaseDate(LocalDate.of(2020,2,20));

        Film film2 = new Film();
        film2.setName("Название фильма 2");
        film2.setDescription("Описание фильма 2");
        film2.setDuration(200);
        film2.setReleaseDate(LocalDate.of(2020,2,20));

        controller.add(film1);
        controller.add(film2);
        List<Film> filmList = controller.getAll();
        assertEquals(film1, filmList.get(0));
        assertEquals(film2, filmList.get(1));
    }
}
