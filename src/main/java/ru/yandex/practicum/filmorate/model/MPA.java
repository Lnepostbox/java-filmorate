package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MPA {
    @Size(min = 1, max = 5)
    private Integer id;
    @NotBlank
    private String name;
    @JsonCreator
    public MPA(Integer id) {
        this.id = id;
    }
}
