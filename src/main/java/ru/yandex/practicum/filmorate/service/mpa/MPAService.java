package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPA.MPAStorage;

import java.util.List;


@Service
public class MPAService {
    private final MPAStorage mpaStorage;

    public MPAService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public MPA get(int mpaId) throws NotFoundException {
        final MPA mpa = mpaStorage.getById(mpaId);
        if (mpa == null) {
            throw new NotFoundException("Mpa with id = " + mpaId + " not found");
        }
        return mpa;
    }

    public List<MPA> getAll() {
        return mpaStorage.getAll();
    }
}