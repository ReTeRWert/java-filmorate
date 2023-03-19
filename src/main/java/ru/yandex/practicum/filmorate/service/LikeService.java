package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.db.LikeDbStorage;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDbStorage likeDbStorage;

    public void addLike(Integer filmId, Integer userId) {
        likeDbStorage.addLike(filmId, userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        likeDbStorage.removeLike(filmId, userId);
    }
}
