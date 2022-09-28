package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @NonNull
    private long id;
    //@NotBlank
    //@Email
    private final String email;
    //@NotBlank
    private final String login;
    @NonNull
    private String name;
    //@PastOrPresent
    private final LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();
}