package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.List;

@Qualifier
@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;


    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sql = " insert into friends (user_id, friend_id) values (?,?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        String sql = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> getFriendsUser(Integer userId) {
        String sql = "select * from users, friends where users.user_id = friends.friend_id and friends.user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> userDbStorage.makeUser(rs), userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {
        String sql = "select * from USERS u, FRIENDS f, FRIENDS o "
                + "where u.user_id = f.friend_id "
                + "and u.user_id = o.friend_id "
                + "and f.user_id= ? and o.user_id=?";
        /* String sql = "select * " +
                "from (select * " +
                      "from users u, friends f where u.user_id = f.friend_id) as sql1 ," +
                "(select * from users u, friends o where u.user_id = o.friend_id) as sql2 " +
                "where sql1.user_id = sql2.user_id and f.user_id =? and o.user_id = ?"; */
        return jdbcTemplate.query(sql, (rs, rowNum) -> userDbStorage.makeUser(rs), userId, otherUserId);
    }
}
