package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data

public class Film {
    private int id;
    @NotBlank (message = "Название фильма не может быть пустым")
    private String name;
    @NotBlank (message = "Описание фильма не может быть пустым")
    @Size (max=200, message = "Описание фильма не должно привышать 200 символов")
    private String description;
    @NotNull  (message = "Дата выпуска фильма не определена")
    private LocalDate releaseDate;
    @Positive (message = "Продолжительность фильма должна быть положительной")
    private int duration;
    private Set<Integer> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(User user) {
        likes.add(user.getId());
    }

    public void removeLike(User user) { likes.remove(user.getId()); }

    public List<Integer> getLikes() { return new ArrayList<>(likes); }

}

