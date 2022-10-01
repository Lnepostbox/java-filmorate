package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
@SuperBuilder
public class Genre {
    @Positive
    private Integer id;
    private String name;
}