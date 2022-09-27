package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping()
    public List<MPA> getAll() {
        log.info("Получение всех рейтингов MPA");
        return mpaService.getAll();
    }

    @GetMapping("{id}")
    public Optional<MPA> getAll(@PathVariable Integer id) {
        log.info("Получение рейтинга MPA по ID " + id);
        Optional<MPA> optionalMPA  = mpaService.getById(id);
        optionalMPA.orElseThrow(() -> new NotFoundException("Рейтинг MPA по ID не найдет"));
        return optionalMPA;
    }
}
