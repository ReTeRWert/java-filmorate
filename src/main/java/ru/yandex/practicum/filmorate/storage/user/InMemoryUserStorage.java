package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.utilites.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private final UserValidator validator = new UserValidator();
    private static int id = 1;


    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {

        validator.validateUser(user);

        if (users.containsValue(user)) {
            throw new ValidateException("Такой пользователь уже есть.");
        }

        user.setId(id);
        users.put(user.getId(), user);
        id++;

        return user;
    }

    @Override
    public User updateUser(User user) {
        validator.validateUser(user);

        if (!users.containsKey(user.getId())) {
            throw new NullPointerException("Такого пользователя еще нет, добавьте его");
        }

        users.replace(user.getId(), user);

        return user;
    }

    public User getUserById(Integer id) {
        if (users.get(id) == null) {
            throw new NullPointerException("Такого пользователя не существует.");
        }
        return users.get(id);
    }

    public List<User> getUserList(Iterable <Integer> friendsId) {
        ArrayList<User> friends = new ArrayList<>();

        for (Integer friendId: friendsId) {
            friends.add(users.get(friendId));
        }

        return friends;
    }
}
