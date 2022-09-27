package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpaDao.MpaStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MpaService {

    private final MpaStorage mpaStorage;

    public MpaService(MpaStorage mpaStorage) { this.mpaStorage = mpaStorage; }

    public List<MPA> getAll() { return mpaStorage.getAll();}

    public Optional<MPA> getById(Integer id) {
        return mpaStorage.getById(id);
    }
}
