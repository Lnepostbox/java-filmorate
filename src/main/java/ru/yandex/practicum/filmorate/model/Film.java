package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data

public class Film {
    private long id;
    @NotNull (message = "Название фильма не определено")
    @NotBlank (message = "Название фильма не может быть пустым")
    private String name;
    @NotNull (message = "Описание фильма не определено")
    @NotBlank (message = "Описание фильма не может быть пустым")
    @Size (max=200, message = "Описание фильма не должно привышать 200 символов")
    private String description;
    @NotNull  (message = "Дата выпуска фильма не определена")
    private LocalDate releaseDate;
    @Positive (message = "Продолжительность фильма должна быть положительной")
    private int duration;
}

