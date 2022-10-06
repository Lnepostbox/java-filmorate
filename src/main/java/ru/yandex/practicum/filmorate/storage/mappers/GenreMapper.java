package ru.yandex.practicum.filmorate.storage.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreMapper {
    public Genre mapGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("genre_id");
        String name = rs.getString("genre_name");
        return new Genre(id, name);
    }
}
