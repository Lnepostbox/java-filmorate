package ru.yandex.practicum.filmorate.storage.likeDao;

public interface LikeStorage {

    void addLike(Integer idFilm, Integer idUser);

    void removeLike(Integer idFilm, Integer idUser);

}
