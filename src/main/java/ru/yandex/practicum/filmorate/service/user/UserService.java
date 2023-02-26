package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryUserStorage userStorage;

    public void addFriend(Integer userId, Integer friendId) {
        userStorage.getUserById(userId)
                .getFriends()
                .add(friendId);

        userStorage.getUserById(friendId)
                .getFriends()
                .add(userId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        userStorage.getUserById(userId)
                .getFriends()
                .remove(friendId);

        userStorage.getUserById(friendId)
                .getFriends()
                .remove(userId);
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

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }

    public void addUser(User user) {
        userStorage.addUser(user);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }
}
