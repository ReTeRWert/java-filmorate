package ru.yandex.practicum.filmorate.storage;

public interface ReviewLikeStorage {
    void addLike(Long reviewId, Long userId);

    void addDislike(Long reviewId, Long userId);


    void deleteLike(Long reviewId, Long userId);

    void deleteDislike(Long reviewId, Long userId);
}
