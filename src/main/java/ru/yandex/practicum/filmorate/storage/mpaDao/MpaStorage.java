package ru.yandex.practicum.filmorate.storage.mpaDao;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {

    List<MPA> getAll();

    Optional<MPA> getById(Integer id);

}
