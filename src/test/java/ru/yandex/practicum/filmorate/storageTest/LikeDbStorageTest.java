/*
package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbStorageTest {
    private final LikeStorage likeStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Test
    public void shouldAddAndRemoveFilmLike() {
        Film film2New = new Film(
                2L,
                "film2new",
                "description2",
                LocalDate.of(2000, 2, 12),
                2000,
                new Mpa(1, "G"),
                new ArrayList<>()
        );

        User user2New = new User(
                2,
                "user@mail.ru",
                "login2",
                "user2new",
                LocalDate.of(2000, 2, 2)
        );

        filmStorage.create(film2New);
        userStorage.create(user2New);

        int filmLikesCountBeforeAdd =  filmStorage.findById(film2New.getId()).get().getLikes().size();

        likeStorage.addLike(film2New, user2New);

        Optional<Film> filmWithLikes = filmStorage.findById(film2New.getId());
        int filmLikesCountAfterAdd = filmStorage.findById(film2New.getId()).get().getLikes().size();

        assertThat(filmWithLikes.get().getLikes()).contains(user2New);
        assertThat(filmLikesCountAfterAdd).isEqualTo(filmLikesCountBeforeAdd + 1);
        likeStorage.removeLike(film2New, user2New);

        Optional<Film> filmWithoutLikes = filmStorage.findById(film2New.getId());
        int filmLikesCountAfterRemove = filmStorage.findById(film2New.getId()).get().getLikes().size();

        assertThat(filmWithoutLikes.get().getLikes()).doesNotContain(user2New);
        assertThat(filmLikesCountAfterRemove).isEqualTo(filmLikesCountAfterAdd - 1);
    }
}*/
