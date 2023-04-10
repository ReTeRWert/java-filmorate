package ru.yandex.practicum.filmorate.storage;

public interface ReviewLikeStorage {

    void addLike(Integer reviewId, Integer userId);

    void addDislike(Integer reviewId, Integer userId);


    void deleteLike(Integer reviewId, Integer userId);

    void deleteDislike(Integer reviewId, Integer userId);

}
