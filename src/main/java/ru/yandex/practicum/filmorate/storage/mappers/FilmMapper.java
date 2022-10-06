package ru.yandex.practicum.filmorate.storage.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmMapper {
    public Film mapFilm(ResultSet rs) throws SQLException {
        long id = rs.getLong("film_id");
        String name = rs.getString("film_name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        List<Genre> genres = new ArrayList<>();
        Mpa mpa = new Mpa(
                rs.getInt("rating_id"),
                rs.getString("rating_name")
        );
        return new Film(id, name, description, releaseDate, duration, mpa, genres);
    }
}
