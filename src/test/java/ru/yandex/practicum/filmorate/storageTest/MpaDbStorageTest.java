package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorageTest {
    private final MpaStorage mpaStorage;

    @Test
    public void shouldFindMpaById() {
        int gMpaId = 1;
        int pgMpaId = 2;
        int pg13MpaId = 3;
        int rMpaId = 4;
        int nc17MpaId = 5;

        String mpa1Name = mpaStorage.findById(gMpaId).get().getName();
        String mpa2Name = mpaStorage.findById(pgMpaId).get().getName();
        String mpa3Name = mpaStorage.findById(pg13MpaId).get().getName();
        String mpa4Name = mpaStorage.findById(rMpaId).get().getName();
        String mpa5Name = mpaStorage.findById(nc17MpaId).get().getName();

        assertThat(mpa1Name).isEqualTo("G");
        assertThat(mpa2Name).isEqualTo("PG");
        assertThat(mpa3Name).isEqualTo("PG-13");
        assertThat(mpa4Name).isEqualTo("R");
        assertThat(mpa5Name).isEqualTo("NC-17");
    }

    @Test
    public void shouldFindAllMpa() {
        int mpaCount = 5;

        assertThat(mpaStorage.findAll().size()).isEqualTo(mpaCount);
    }
}