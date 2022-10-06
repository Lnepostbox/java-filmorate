package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.controller.MpaController;
import ru.yandex.practicum.filmorate.controller.UserController;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final UserController userController;
	private final FilmController filmController;
	private final GenreController genreController;
	private final MpaController mpaController;

	@Test
	void testIncludeControllersByDependency() {
		Assertions.assertNotNull(userController, "UserController не передан через зависимость");
		Assertions.assertNotNull(filmController, "FilmController не передан через зависимость");
		Assertions.assertNotNull(genreController, "GenreController не передан через зависимость");
		Assertions.assertNotNull(mpaController, "RatingMpaController не передан через зависимость");
	}

}
