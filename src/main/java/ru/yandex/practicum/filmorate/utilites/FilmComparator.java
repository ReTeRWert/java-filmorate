package ru.yandex.practicum.filmorate.utilites;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {

    @Override
    public int compare(Film f1, Film f2) {
        if (f1.getLikes() == null && f2.getLikes() == null) return 0;
        else if (f1.getLikes() == null && f2.getLikes() != null) return 1;
        else if (f1.getLikes() != null && f2.getLikes() == null) return -1;
        else if (f1.getLikes().size() > f2.getLikes().size()) return -1;
        else return 1;
    }
}
