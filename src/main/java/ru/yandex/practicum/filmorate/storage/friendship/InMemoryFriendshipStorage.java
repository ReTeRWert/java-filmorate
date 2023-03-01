package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Data
@Component
public class InMemoryFriendshipStorage implements FriendshipStorage {

    @Autowired
    private final UserStorage userStorage;

    public void addFriend(long userId, long friendId) {
        userStorage.get(userId).getFriends().put(friendId, true);
        userStorage.get(friendId).getFriends().put(userId, true);
    }

    public Collection<User> getFriendsUser(long userId) {
        Collection<User> friendsUser = new ArrayList<>();
        for (Long n : userStorage.get(userId).getFriends().keySet()) friendsUser.add(userStorage.get(n));
        /*for (long id : userStorage.get(userId).getFriends().keySet()){
            userCollection.add(userStorage.get(id));
        }*/
        return friendsUser;
    }

    public Collection<User> getCommonFriends(long userId, long friendId) {
        HashMap<Long, Boolean> friendListFirst = new HashMap<>(userStorage.get(userId).getFriends());
        HashMap<Long, Boolean> friendListSecond = new HashMap<>(userStorage.get(friendId).getFriends());
        Collection<User> friendsUser = new ArrayList<>();
        friendListFirst.values().retainAll(friendListSecond.values());
        for (Long n : friendListFirst.keySet()) friendsUser.add(userStorage.get(n));
        return friendsUser;
    }

    public boolean deleteFriend(long userId, long friendId) {

        return userStorage.get(userId).getFriends().remove(friendId);
    }
}
