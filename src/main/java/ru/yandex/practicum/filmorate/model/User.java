package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@SuperBuilder
public class User {
    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d]*$")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;

}