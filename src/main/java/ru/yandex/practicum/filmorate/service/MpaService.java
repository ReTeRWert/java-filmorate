package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaDbStorage ratingMpaStorage;

    public Mpa getRatingById(Integer id) {
        return ratingMpaStorage.getRatingById(id);
    }

    public List<Mpa> getRatings() {
        return ratingMpaStorage.getRatings();
    }
}
