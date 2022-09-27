package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.userDao.UserStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDaoTest {
    private final UserStorage userStorage;

    @Test
    public void shouldCreate() {
        User user4 = new User(4,
                "user4@email.ru",
                "login4",
                "user4",
                LocalDate.of(2000, 4, 4)
        );

        int usersCountBeforeAdd = userStorage.getAll().size();
        userStorage.create(user4);
        int usersCountAfterAdd = userStorage.getAll().size();

        assertThat(usersCountAfterAdd).isEqualTo(usersCountBeforeAdd + 1);
        assertThat(userStorage.getAll()).contains(user4);
    }

    @Test
    public void shouldUpdate() {
        User user1new  = new User(
                1,
                "user1new@mail.ru",
                "login1",
                "user1",
                LocalDate.of(2000, 1, 1)
        );

        String userOldEmail = userStorage.getById(1).get().getEmail();
        int usersCountBeforeUpdate = userStorage.getAll().size();

        assertThat(userOldEmail).isEqualTo("user1@mail.ru");
        userStorage.update(user1new);

        String userNewEmail = userStorage.getById(1).get().getEmail();
        int usersCountAfterUpdate = userStorage.getAll().size();

        assertThat(userNewEmail).isEqualTo("user1new@mail.ru");
        assertThat(usersCountBeforeUpdate).isEqualTo(usersCountAfterUpdate);
    }

    @Test
    public void shouldGetById() {
        int userId = 1;
        Optional<User> user1 = userStorage.getById(userId);

        assertThat(user1)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("id", userId));
    }

    @Test
    public void shouldGetAll() {
        assertThat(userStorage.getAll().size()).isEqualTo(4);
    }
}
