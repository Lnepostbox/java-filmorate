package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Film {
    @NonNull
    @Min(0)
    private long id;
    @NonNull
    @NotBlank
    private final String name;
    @NonNull
    @NotBlank
    @Size(max=200)
    private final String description;
    @NonNull
    private final LocalDate releaseDate;
    @NonNull
    @Positive
    private final long duration;
    private final Mpa mpa;
    private final List<Genre> genres;
    private final Set<User> likes = new HashSet<>();
}

