package ru.yandex.practicum.filmorate.storage.filmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Film> create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        Integer id = simpleJdbcInsert.executeAndReturnKey(mapFilm(film)).intValue();

        if (film.getGenres() != null) {
            String sqlInsertFilmGenre = "INSERT INTO film_genres (FILM_ID,GENRE_ID) values (?,?)";
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlInsertFilmGenre, id, genre.getId());
            }
        }
        return getById(id);
    }

    @Override
    public Optional<Film> update(Film film) {
        String sql = "update FILMS set NAME  = ?, DESCRIPTION = ?, RELEASEDATE = ?, DURATION = ?,RATING_MPA_ID = ? where FILM_ID = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        String sqlDelGenreFilm = "DELETE FROM film_genres WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlDelGenreFilm, film.getId());

        if (film.getGenres() != null) {
            String sqlInsertFilmGenre = "INSERT INTO film_genres (FILM_ID,GENRE_ID) values (?,?)";
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlInsertFilmGenre, film.getId(), genre.getId());
            }
        }
        return getById(film.getId());
    }

    @Override
    public List<Film> getAll() {
        String sql = "select  film_id, f.name as fname, description, releaseDate, duration, " +
                "f.RATING_MPA_ID, rm.name as mpa_name " +
                "from films as f join rating_mpa as rm on f.RATING_MPA_ID = rm.RATING_MPA_ID";

        String sql_film = "select fg.FILM_ID,g.GENRE_ID,g.NAME " +
                "from film_genres fg join GENRES g on g.GENRE_ID = fg.GENRE_ID";

        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sql_film);
        Map<Integer, Set<Genre>> setMap = mapGenre(genreRows);
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs, setMap));
    }

    @Override
    public Optional<Film> getById(Integer id) {
        String sql = "select  f.film_id, f.name, description, releaseDate, duration, " +
                "rm.RATING_MPA_ID, rm.name as mpa_name " +
                "from films as f join rating_mpa as rm on f.rating_mpa_id = rm.RATING_MPA_ID" +
                " where f.film_id = ?";

        String sql_film = "select fg.FILM_ID,g.GENRE_ID,g.NAME " +
                "from film_genres fg join GENRES g on g.GENRE_ID = fg.GENRE_ID " +
                "WHERE fg.FILM_ID = ?";

        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sql_film, id);
        Map<Integer, Set<Genre>> setMap = mapGenre(genreRows);
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, id);

        if (filmRows.next()) {
            log.info("Фильм с ID {} найден: {}", filmRows.getString("film_id"), filmRows.getString("NAME"));
            Film film = new Film(filmRows.getInt("film_id"),
                    filmRows.getString("NAME"),
                    filmRows.getString("description"),
                    Objects.requireNonNull(filmRows.getDate("releaseDate")).toLocalDate(),
                    filmRows.getInt("duration"),
                    new MPA(filmRows.getInt("RATING_MPA_ID"), filmRows.getString("MPA_NAME")),
                    setMap.getOrDefault(id, new HashSet<>()));
            return Optional.of(film);
        } else {
            log.info("Фильм с ID {} не найден", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Film> getPopular(Integer count) {

        String sql = "select  film_id, f.name as fname, description, releaseDate, duration, " +
                "f.RATING_MPA_ID, rm.name as mpa_name " +
                "from films as f join rating_mpa as rm on f.RATING_MPA_ID = rm.RATING_MPA_ID " +
                "ORDER BY f.LIKES_COUNTER DESC " +
                "LIMIT ?";

        String sql_film = "select fg.FILM_ID,g.GENRE_ID,g.NAME " +
                "from film_genres fg join GENRES g on g.GENRE_ID = fg.GENRE_ID";

        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sql_film);
        Map<Integer, Set<Genre>> setMap = mapGenre(genreRows);

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs, setMap), count);
    }

    private Film makeFilm(ResultSet filmRows, Map<Integer, Set<Genre>> setMap) throws SQLException {
        Integer film_id = filmRows.getInt("film_id");
        return new Film(film_id,
                filmRows.getString("name"),
                filmRows.getString("description"),
                filmRows.getDate("releaseDate").toLocalDate(),
                filmRows.getInt("duration"),
                new MPA(filmRows.getInt("RATING_MPA_ID"), filmRows.getString("mpa_name")),
                setMap.getOrDefault(film_id, new HashSet<>()));
    }

    private Map<String, Object> mapFilm(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("releaseDate", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("RATING_MPA_ID", film.getMpa().getId());
        return values;
    }

    private Map<Integer, Set<Genre>> mapGenre(SqlRowSet genresRowSet) {
        Map<Integer, Set<Genre>> genreMap = new HashMap<>();
        while (genresRowSet.next()) {
            int film_id = genresRowSet.getInt("film_id");
            int genre_id = genresRowSet.getInt("GENRE_ID");
            String genre_name = genresRowSet.getString("NAME");

            if (genreMap.containsKey(film_id)) {
                genreMap.get(film_id).add(new Genre(genre_id, genre_name));
            } else {
                genreMap.put(film_id, new HashSet<>());
                genreMap.get(film_id).add(new Genre(genre_id, genre_name));
            }
        }
        return genreMap;
    }

}