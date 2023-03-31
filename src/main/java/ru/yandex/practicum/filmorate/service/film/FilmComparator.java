package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        if (o1.getLikes() == null) {
            if (o2.getLikes() == null) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return 1;
            }
        } else {
            if (o2.getLikes() == null) {
                return -1;
            } else {
                if (o1.getLikes().size() == o2.getLikes().size()) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return o2.getLikes().size() - o1.getLikes().size();
                }
            }
        }
    }
}