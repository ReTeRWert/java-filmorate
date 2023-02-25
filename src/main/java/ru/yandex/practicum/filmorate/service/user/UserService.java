package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    InMemoryFilmStorage filmStorage;
    InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addFriend(Integer user, Integer friend) {
        userStorage.getUserById(user)
                .getFriends()
                .add(userStorage.getUserById(friend).getId());

        userStorage.getUserById(friend)
                .getFriends()
                .add(userStorage.getUserById(user).getId());
    }

    public void removeFriend(Integer user, Integer friend) {
        userStorage.getUserById(user)
                .getFriends()
                .remove(userStorage.getUserById(friend).getId());

        userStorage.getUserById(friend)
                .getFriends()
                .remove(userStorage.getUserById(user).getId());
    }

    public List<User> getListGeneralFriends(Integer verifiableUser, Integer comparedUser) {

        HashSet<Integer> retain = new HashSet<>(userStorage.getUserById(verifiableUser).getFriends());
        retain.retainAll(userStorage.getUserById(comparedUser).getFriends());

        ArrayList<User> friends = new ArrayList<>();
        for (Integer friendId: retain) {
            friends.add(userStorage.getUserById(friendId));
        }
        return friends;
    }

    public List<User> getFriends(Integer id) {
        return userStorage.getUserList(userStorage.getUserById(id).getFriends());
    }
}
