package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Mpa {
    @NonNull
    @Min(0)
    private final int id;
    @NonNull
    private final String name;
}
