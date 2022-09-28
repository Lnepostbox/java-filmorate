package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendshipDbStorageTest {
    private final FriendshipStorage friendshipStorage;
    private final UserStorage userStorage;

    @Test
    public void shouldAddAndRemoveFriend() {
        User user2New = new User(
                2,
                "user2@mail.ru",
                "login2",
                "user2new",
                LocalDate.of(2000, 2, 2)
        );

        User user3New = new User(
                3,
                "user3@mail.ru",
                "login3",
                "user3new",
                LocalDate.of(2000, 3, 3)
        );

        userStorage.create(user2New);
        userStorage.create(user3New);

        int userFriendsCountBeforeAdd = userStorage.findFriends(user2New).size();

        assertThat(userFriendsCountBeforeAdd).isEqualTo(0);
        friendshipStorage.addFriend(user2New, user3New);

        int userFriendsCountAfterAdd = userStorage.findFriends(user2New).size();

        assertThat(userFriendsCountAfterAdd).isEqualTo(1);

        friendshipStorage.removeFriend(user2New, user3New);
        assertThat(userFriendsCountBeforeAdd).isEqualTo(0);
    }
}
