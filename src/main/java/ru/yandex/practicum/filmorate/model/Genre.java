package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Genre {
    @Size(min = 1, max = 6)
    private Integer id;
    @NotBlank
    private String name;
    @JsonCreator
    public Genre(Integer id) {
        this.id = id;
    }
}
