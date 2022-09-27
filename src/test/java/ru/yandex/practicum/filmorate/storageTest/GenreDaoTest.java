package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genreDao.GenreStorage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDaoTest {
    private final GenreStorage genreStorage;

    @Test
    public void shouldFindGenreById() {
        final int comedyGenreId = 1;
        final int dramaGenreId = 2;
        final int cartoonGenreId = 3;
        final int thrillerGenreId = 4;
        final int documentaryGenreId = 5;
        final int actionGenreId = 6;

        String genre1Name = genreStorage.getById(comedyGenreId).get().getName();
        String genre2Name = genreStorage.getById(dramaGenreId).get().getName();
        String genre3Name = genreStorage.getById(cartoonGenreId).get().getName();
        String genre4Name = genreStorage.getById(thrillerGenreId).get().getName();
        String genre5Name = genreStorage.getById(documentaryGenreId).get().getName();
        String genre6Name = genreStorage.getById(actionGenreId).get().getName();

        assertThat(genre1Name).isEqualTo("Комедия");
        assertThat(genre2Name).isEqualTo("Драма");
        assertThat(genre3Name).isEqualTo("Мультфильм");
        assertThat(genre4Name).isEqualTo("Триллер");
        assertThat(genre5Name).isEqualTo("Документальный");
        assertThat(genre6Name).isEqualTo("Боевик");
    }

    @Test
    public void shouldFindAllGenres() {
        final int genresCount = 6;

        assertThat(genreStorage.getAll().size()).isEqualTo(genresCount);
    }
}
