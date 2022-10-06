package ru.yandex.practicum.filmorate.storage.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MpaMapper {
    public Mpa mapMpa(ResultSet rs) throws SQLException {
        int id = rs.getInt("rating_id");
        String name = rs.getString("rating_name");
        return new Mpa(id, name);
    }
}
