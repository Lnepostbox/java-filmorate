package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private final UserController controller = new UserController();

    @Test
    void addUser() {
        final User user = new User();
        user.setEmail("userMail@mail.com");
        user.setLogin("userLogin");
        user.setName("userName");
        user.setBirthday(LocalDate.of(2000,2,20));

        controller.add(user);

        assertFalse(controller.getAll().isEmpty());
        assertEquals("userName", controller.getAll().get(0).getName());
    }

    @Test
    void add_LoginUser() {
        final User user = new User();
        user.setEmail("userMail@mail.com");
        user.setLogin("user Login");
        user.setName("userName");
        user.setBirthday(LocalDate.of(2000,2,20));

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.add(user)
        );
        assertEquals("Логин пользователя не может содержать пробелы", exception.getMessage());
    }

    @Test
    void addNullNameUser() {
        final User user = new User();
        user.setEmail("userMail@mail.com");
        user.setLogin("userLogin");
        user.setName(null);
        user.setBirthday(LocalDate.of(2000,2,20));

        controller.add(user);

        assertFalse(controller.getAll().isEmpty());
        assertEquals("userLogin", controller.getAll().get(0).getName());
    }

    @Test
    void addBlankNameUser() {
        final User user = new User();
        user.setEmail("userMail@mail.com");
        user.setLogin("userLogin");
        user.setName("");
        user.setBirthday(LocalDate.of(2000,2,20));

        controller.add(user);

        assertFalse(controller.getAll().isEmpty());
        assertEquals("userLogin", controller.getAll().get(0).getName());
    }

    @Test
    void updateUser() {
        final User user = new User();
        user.setEmail("userMail@mail.com");
        user.setLogin("userLogin");
        user.setName("userName");
        user.setBirthday(LocalDate.of(2000,2,20));

        controller.add(user);

        final User userNew = new User();
        userNew.setId(1);
        userNew.setEmail("userNewMail@mail.com");
        userNew.setLogin("userNewLogin");
        userNew.setName("userNewName");
        userNew.setBirthday(LocalDate.of(2000,2,20));

        controller.update(userNew);

        assertNotEquals(user, controller.getAll().get(0));
        assertEquals("userNewName", controller.getAll().get(0).getName());
    }

    @Test
    void updateNotExistingUser() {
        final User user = new User();
        user.setEmail("userMail@mail.com");
        user.setLogin("userLogin");
        user.setName("userName");
        user.setBirthday(LocalDate.of(2000,2,20));

        controller.add(user);
        user.setId(100);

        final ValidationException exception = assertThrows(
                ValidationException.class, ()-> controller.update(user)
        );
        assertEquals("Пользователь c id 100 не найден", exception.getMessage());
    }

    @Test
    void getAllUsers() {
        final User user1 = new User();
        user1.setEmail("user1Mail@mail.com");
        user1.setLogin("user1Login");
        user1.setName("user1Name");
        user1.setBirthday(LocalDate.of(2000,2,20));

        final User user2 = new User();
        user2.setEmail("user2Mail@mail.com");
        user2.setLogin("user2Login");
        user2.setName("user2Name");
        user2.setBirthday(LocalDate.of(2000,2,20));

        controller.add(user1);
        controller.add(user2);
        List<User> userAll = controller.getAll();
        assertEquals(user1, userAll.get(0));
        assertEquals(user2, userAll.get(1));
    }
}