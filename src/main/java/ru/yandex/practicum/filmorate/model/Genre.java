package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@SuperBuilder
public class Genre {
    private Integer id;
    @NotBlank
    private String name;
}