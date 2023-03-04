package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{

    private long lastId = 0L;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getUsers(){
        return users.values();
    }

    @Override
    public User get(long id){
        if (users.containsKey(id)){
            return users.get(id);
        }else{
            throw new NotFoundException("Пользователя с таким ID нет в базе");
        }
    }

    @Override
    public User create(User user ){
        user.setId(++lastId);
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public User update(User user){
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        }else {
            throw new NotFoundException("Пользователя с таким ID нет в базе");
        }
    }

    @Override
    public boolean delete(long id){
        if(users.containsKey(id)){
            return users.remove(id,users.get(id));
        } else{
            throw new NotFoundException("Пользователя с таким ID нет в базе");
        }

    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (users.get(userId) != null && users.get(friendId) != null) {
            HashMap friendsMap = users.get(userId).getFriends();
            users.get(userId).getFriends().put(friendId, true);
            users.get(friendId).getFriends().put(userId, true);
        } else {
            throw new NotFoundException("Ошибка, пользователь не найден");
        }
    }

    @Override
    public Collection<User> getCommonFriends(Long userId, Long friendId) {
        if (users.get(userId) != null && users.get(friendId) != null) {
            HashMap<Long, Boolean> friendListFirst = new HashMap<>(users.get(userId).getFriends());
            HashMap<Long, Boolean> friendListSecond = new HashMap<>(users.get(friendId).getFriends());
            Collection<User> friendsUser = new ArrayList<>();

            friendListFirst.keySet().retainAll(friendListSecond.keySet());
            for (Long n : friendListFirst.keySet()) friendsUser.add(users.get(n));
            return friendsUser;
        } else {
            return new ArrayList<>();
            //throw new NotFoundException("Ошибка, пользователь не найден");
        }
    }

    @Override
    public Collection<User> getFriendsUser(Long userId) {
        Collection<User> friendsUser = new ArrayList<>();
        for (Long n : users.get(userId).getFriends().keySet()) friendsUser.add(users.get(n));
        /*for (long id : userStorage.get(userId).getFriends().keySet()){
            userCollection.add(userStorage.get(id));
        }*/
        return friendsUser;
    }

    @Override
    public boolean deleteFriend(long userId, long friendId) {
        if (users.get(userId).getFriends().remove(friendId)) {
            return true;
        } else {
            throw new NotFoundException("Таких друзей, нет в базе");
        }
    }
}
