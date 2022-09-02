package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data

public class User {
    private int id;
    @NotBlank (message = "Адрес электронной почты пользователя не может быть пустым")
    @Email (message = "Адрес электронной почты пользователя введен некорректно")
    private String email;
    @NotBlank (message = "Логин пользователя не может быть пустым")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent (message = "Дата рождения пользователя не может быть в будущем")
    private LocalDate birthday;
    private Set<Integer> friendsId = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriend(User user) {
        friendsId.add(user.getId());
    }

    public void removeFriend(User user) {
        friendsId.remove(user.getId());
    }

    public List<Integer> getFriends() {
        return new ArrayList<>(friendsId);
    }

}
