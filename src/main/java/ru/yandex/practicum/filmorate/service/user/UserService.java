package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Data
@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendshipStorage friendshipStorage){
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User getUser(Long userId) throws NotFoundException {
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        return user;
    }
    public void delete(Long id) throws NotFoundException {
        if (userStorage.get(id) != null) {
            userStorage.delete(id);
        } else {
            throw new NotFoundException("User with id = " + id + " not found");
        }
    }
    public void addFriend(long userId, long friendId) throws NotFoundException {
        if(userId == friendId){
            log.debug("Пользователь не может быть другом самому себе");
            throw new ValidationException("Ошибка валидации");
        }
        final User user = userStorage.get(userId);
        final User friend = userStorage.get(friendId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else if (friend == null) {
            throw new NotFoundException("User with id = " + friendId + " not found");
        } else {
            friendshipStorage.addFriend(userId, friendId);
        }
    }

    public void deleteFriend(long idUser, long idFriend){
        if(!(friendshipStorage.deleteFriend(idUser,idFriend))){
            throw new NotFoundException("Not friendship");
        }
    }

    public Collection<User> getFriendsUser(long userId) throws NotFoundException {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else {
            Collection<User> friendsList = friendshipStorage.getFriendsUser(userId);
            return friendsList;
        }
    }
}
