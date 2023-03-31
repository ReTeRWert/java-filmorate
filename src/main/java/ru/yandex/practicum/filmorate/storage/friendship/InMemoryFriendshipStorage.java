package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryFriendshipStorage implements FriendshipStorage {
    private final UserStorage userStorage;

    public InMemoryFriendshipStorage(@Qualifier("inMemoryUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        Set<Long> idFriendsForUser = new HashSet<>();
        if (userStorage.get(userId).getFriends() != null) {
            idFriendsForUser = userStorage.get(userId).getFriends();
        }
        idFriendsForUser.add(friendId);
        userStorage.get(userId).setFriends(idFriendsForUser);
    }

    @Override
    public List<User> getFriendsForUser(Long userId) {
        List<User> friendsList = new ArrayList<>();
        if (userStorage.get(userId).getFriends() != null) {
            for (Long id : userStorage.get(userId).getFriends()) {
                friendsList.add(userStorage.get(id));
            }
        }
        return friendsList;
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        List<User> commonFriends = new ArrayList<>();

        if (userStorage.get(userId).getFriends() == null || userStorage.get(otherUserId).getFriends() == null) {
            return List.of();
        } else {
            Set<Long> duplicateFriendsUser = new HashSet<>(userStorage.get(userId).getFriends());
            duplicateFriendsUser.retainAll(userStorage.get(otherUserId).getFriends());
            for (Long id : duplicateFriendsUser) {
                commonFriends.add(userStorage.get(id));
            }
            return commonFriends;
        }
    }

    @Override
    public void deleteFromFriends(Long userId, Long friendId) {
        Set<Long> idFriendsForUser = new HashSet<>();
        if (userStorage.get(userId).getFriends() != null) {
            idFriendsForUser = userStorage.get(userId).getFriends();
        }
        idFriendsForUser.remove(friendId);
        userStorage.get(userId).setFriends(idFriendsForUser);
    }
}