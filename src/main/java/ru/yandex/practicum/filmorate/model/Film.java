package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
public class Film {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(max=200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;
    @NotNull
    private Mpa mpa;
    private List<Genre> genres;
}

