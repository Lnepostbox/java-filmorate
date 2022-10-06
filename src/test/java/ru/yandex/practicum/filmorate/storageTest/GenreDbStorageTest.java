package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.implementations.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.implementations.GenreDbStorage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {
    private final GenreDbStorage genreStorage;
    private final FilmDbStorage filmStorage;

    @Test
    void shouldReadById() {
        Optional<Genre> genreOptional = Optional.ofNullable(genreStorage.readById(1));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void shouldReadAll() {
        List<Genre> genres = genreStorage.readAll();
        assertThat(genres).hasSize(6);
    }

    @Test
    void shouldReadByFilmId() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .duration(150)
                .releaseDate(LocalDate.parse("2000-01-01"))
                .mpa(Mpa.builder().id(1).build())
                .build();
        Long filmId = filmStorage.create(film).getId();
        List<Genre> testGenres = genreStorage.readAll().subList(0,5);
        genreStorage.createForFilm(filmId, testGenres);
        List<Genre> genres = genreStorage.readByFilmId(filmId);
        assertThat(genres).hasSize(5).containsAll(testGenres);
    }

}
