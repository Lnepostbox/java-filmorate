package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data

public class User {
    private long id;
    @NotNull (message = "Адрес электронной почты пользователя не определен")
    @NotBlank (message = "Адрес электронной почты пользователя не может быть пустым")
    @Email (message = "Адрес электронной почты пользователя введен некорректно")
    private String email;
    @NotNull (message = "Логин пользователя не определен")
    @NotBlank (message = "Логин пользователя не может быть пустым")
    private String login;
    private String name;
    @PastOrPresent (message = "Дата рождения пользователя не может быть в будущем")
    private LocalDate birthday;
}
