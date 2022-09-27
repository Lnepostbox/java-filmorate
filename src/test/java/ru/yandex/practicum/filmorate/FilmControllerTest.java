package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FilmControllerTest {
    FilmController filmController;
    UserStorage userStorage;
    FilmStorage filmStorage;
    FilmService filmService;
    UserService userService;
    @BeforeEach
    public void beforeEach() {
        userStorage = new InMemoryUserStorage();
        filmStorage = new InMemoryFilmStorage();
        userService = new UserService(userStorage);
        filmService = new FilmService(filmStorage, userService);
        filmController = new FilmController(filmService);
    }

    @Test
    void addFilm() {
        Film film = new Film("Название фильма", "Описание фильма",
                LocalDate.of(2020,2,20), 200);

        filmController.create(film);

        assertFalse(filmController.getAll().isEmpty());
        assertEquals("Название фильма",filmController.getAll().get(0).getName());
    }

    @Test
    void addEarlier1895y12m28dDateFilm() {
        Film film = new Film("Название фильма", "Описание фильма",
                LocalDate.of(1895,12,27), 200);

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> filmController.create(film)
        );
        assertEquals("Дата релиза фильма не может быть ранее 28 декабря 1895 года",exception.getMessage());
    }

    @Test
    void updateFilm() {
        Film film = new Film("Название фильма","Описание фильма",
                LocalDate.of(2020,2,20), 200);

        filmController.create(film);

        Film filmNew = new Film("Название фильма новое", "Описание фильма",
                LocalDate.of(2020,2,20), 200);
        filmNew.setId(1);

        filmController.update(filmNew);

        assertNotEquals(film, filmController.getAll().get(0));
        assertEquals("Название фильма новое", filmController.getAll().get(0).getName());
    }

    @Test
    void updateNotExistingFilm() {
        Film film = new Film("Название фильма", "Описание фильма",
                LocalDate.of(2020,2,20), 200);

        filmController.create(film);
        film.setId(100);

        final NotFoundException exception = assertThrows(
                NotFoundException.class, ()-> filmController.update(film)
        );
        assertEquals("Фильм не найден", exception.getMessage());
    }

    @Test
    void getAllFilms() {
        Film film1 = new Film("Название фильма 1","Описание фильма 1",
                LocalDate.of(2020,2,20), 200);


        Film film2 = new Film("Название фильма 2", "Описание фильма 2",
                LocalDate.of(2020,2,20), 200);

        filmController.create(film1);
        filmController.create(film2);
        List<Film> filmList = filmController.getAll();
        assertEquals(film1, filmList.get(0));
        assertEquals(film2, filmList.get(1));
    }
}
