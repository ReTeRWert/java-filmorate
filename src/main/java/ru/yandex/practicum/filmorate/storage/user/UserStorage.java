package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user);

    void remove(Long userId);

    User get(Long id);

    User update(User user);

    void deleteAll();

    Collection<User> getAll();
}
