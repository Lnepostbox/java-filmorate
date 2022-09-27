/*
package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.impl.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    UserController userController;
    UserStorage userStorage;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        userController = new UserController(userService);
    }

    @Test
    void addUser() {
        final User user = new User(id, "userMail@mail.com", "userMail@mail.com", "userName",
                LocalDate.of(2000,2,20));

            userController.create(user);

        assertFalse(userController.getAll().isEmpty());
        assertEquals("userName", userController.getAll().get(0).getName());
    }

    @Test
    void add_LoginUser() {
        final User user = new User(id, "userMail@mail.com", "user Login", "userName",
                LocalDate.of(2000,2,20));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> userController.create(user)
        );
        assertEquals("Логин пользователя не может содержать пробелы", exception.getMessage());
    }

    @Test
    void addNullNameUser() {
        final User user = new User(id, "userMail@mail.com", "userLogin", null,
                LocalDate.of(2000,2,20));

        userController.create(user);

        assertFalse(userController.getAll().isEmpty());
        assertEquals("userLogin", userController.getAll().get(0).getName());
    }

    @Test
    void addBlankNameUser() {
        final User user = new User(id, "userMail@mail.com", "userLogin", "",
                LocalDate.of(2000,2,20));

        userController.create(user);

        assertFalse(userController.getAll().isEmpty());
        assertEquals("userLogin", userController.getAll().get(0).getName());
    }

    @Test
    void updateUser() {
        final User user = new User(id, "userMail@mail.com", "userLogin", "userName",
                LocalDate.of(2000,2,20));

        userController.create(user);

        final User userNew = new User(id 1, "userNewMail@mail.com", "userNewLogin", "userNewName",
                LocalDate.of(2000,2,20));

        userController.update(userNew);

        assertNotEquals(user, userController.getAll().get(0));
        assertEquals("userNewName", userController.getAll().get(0).getName());
    }

    @Test
    void updateNotExistingUser() {
        final User user = new User(id 100, "userMail@mail.com", "userLogin", "userName",
                LocalDate.of(2000,2,20));

        userController.create(user);

        final NotFoundException exception = assertThrows(
                NotFoundException.class, ()-> userController.update(user)
        );
        assertEquals("Пользователь не найден", exception.getMessage());
    }

    @Test
    void getAllUsers() {
        final User user1 = new User(id, "user1Mail@mail.com", "user1Login", "user1Name",
                LocalDate.of(2000,2,20));

        final User user2 = new User(id, "user2Mail@mail.com", "user2Login", "user2Name",
                LocalDate.of(2000,2,20));

        userController.create(user1);
        userController.create(user2);
        List<User> userAll = userController.getAll();
        assertEquals(user1, userAll.get(0));
        assertEquals(user2, userAll.get(1));
    }
}*/
