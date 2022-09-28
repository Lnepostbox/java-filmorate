package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {
    private final GenreStorage genreStorage;

    @Test
    public void shouldFindGenreById() {
        final int comedyGenreId = 1;
        final int dramaGenreId = 2;
        final int cartoonGenreId = 3;
        final int thrillerGenreId = 4;
        final int documentaryGenreId = 5;
        final int actionGenreId = 6;

        String genre1Name = genreStorage.findById(comedyGenreId).getName();
        String genre2Name = genreStorage.findById(dramaGenreId).getName();
        String genre3Name = genreStorage.findById(cartoonGenreId).getName();
        String genre4Name = genreStorage.findById(thrillerGenreId).getName();
        String genre5Name = genreStorage.findById(documentaryGenreId).getName();
        String genre6Name = genreStorage.findById(actionGenreId).getName();

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

        assertThat(genreStorage.findAll().size()).isEqualTo(genresCount);
    }
}
