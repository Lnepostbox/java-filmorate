package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @BeforeEach
    public void beforeEach() {
        User user1 = userStorage.findById(1L);
        User user2 = userStorage.findById(2L);
        User user3 = userStorage.findById(3L);

        friendshipStorage.removeFriend(user1, user2);
        friendshipStorage.removeFriend(user1, user3);
    }

    @Test
    public void shouldFindUserById() {
        long userId = 1L;
        Optional<User> userOptional = Optional.ofNullable(userStorage.findById(userId));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("id", userId));
    }

    @Test
    public void shouldCreateUser() {
        User user4 = new User(
                4L,
                "user4@mail.ru",
                "login4",
                "user4",
                LocalDate.of(2000, 4, 4)
        );

        int usersCountBefore = userStorage.findAll().size();
        userStorage.create(user4);
        int usersCountAfter = userStorage.findAll().size();

        assertThat(usersCountAfter).isEqualTo(usersCountBefore + 1);
        assertThat(userStorage.findAll()).contains(user4);
    }

    @Test
    public void shouldUpdateUser() {
        User user1 = userStorage.findById(1L);
        User user1New  = new User(
                1L,
                "user1new@mail.ru",
                "login1",
                "user1",
                LocalDate.of(2000, 1, 1)

        );

        String userOldEmail = userStorage.findById(user1.getId()).getEmail();
        int usersCountBeforeUpdate = userStorage.findAll().size();

        assertThat(userOldEmail).isEqualTo("user1@mail.ru");
        userStorage.update(user1New);

        String userNewEmail = userStorage.findById(user1New.getId()).getEmail();
        int usersCountAfterUpdate = userStorage.findAll().size();

        assertThat(userNewEmail).isEqualTo("user1new@mail.ru");
        assertThat(usersCountBeforeUpdate).isEqualTo(usersCountAfterUpdate);
    }

    @Test
    public void shouldFindUserFriends() {
        User user1 = userStorage.findById(1L);
        User user2 = userStorage.findById(2L);
        User user3 = userStorage.findById(3L);

        int userFriendsCountBeforeAdd = userStorage.findFriends(user1).size();

        friendshipStorage.addFriend(user1, user2);
        friendshipStorage.addFriend(user1, user3);

        List<User> userFriends = userStorage.findFriends(user1);
        int userFriendsCountAfterAdd = userFriends.size();

        assertThat(userFriendsCountAfterAdd).isEqualTo(userFriendsCountBeforeAdd + 2);
        assertThat(userFriends).contains(user2);
        assertThat(userFriends).contains(user3);
    }

    @Test
    public void shouldFindCommonFriends() {
        User user1 = userStorage.findById(1L);
        User user2 = userStorage.findById(2L);
        User user3 = userStorage.findById(3L);

        friendshipStorage.addFriend(user1, user3);
        friendshipStorage.addFriend(user2, user3);

        List<User> commonFriends = userStorage.findCommonFriends(user1, user2);

        assertThat(commonFriends).contains(user3);
        assertThat(commonFriends.size()).isEqualTo(1);

        friendshipStorage.removeFriend(user1, user3);
        friendshipStorage.removeFriend(user2, user3);
    }
}
