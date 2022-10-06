package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.util.List;


@Slf4j
@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa readById(Integer id) {
        Mpa mpa = mpaStorage.readById(id);
        validate(mpa);
        return mpa;
    }

    public List<Mpa> readAll() {
        return mpaStorage.readAll();
    }

    public void validate(Mpa mpa) {
        if (mpa == null) {
            log.warn("Попытка действия с несуществующим id оейтинга МРА");
            throw new NotFoundException("Рейтинг МРА с таким id не существует");
        }

        if (mpa.getId() < 1) {
            log.warn("Попытка действия с отрицательным id рейтинга МРА: {}", mpa.getId());
            throw new NotFoundException("Рейтинг МРА не может иметь отрицательный id"); }
    }
}