package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getUsers() throws RuntimeException {
        return users.values();
    }

    @Override
    public User get(long id) throws RuntimeException {
        return users.get(id);
    }

    @Override
    public User add(User user) throws RuntimeException {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(long id) throws RuntimeException {
        users.remove(id);
    }
}
