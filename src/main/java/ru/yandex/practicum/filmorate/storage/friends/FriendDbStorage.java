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
        String sql = "INSERT INTO friends(user_id, friend_id) " +
                "VALUES (?,?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM friends " +
                "WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> getFriendsUser(Integer userId) {
        String sql = "SELECT * " +
                "FROM users AS u, friends AS f " +
                "WHERE u.user_id = f.friend_id AND f.user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> userDbStorage.makeUser(rs), userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {
        String sql = "SELECT * " +
                "FROM users AS u, friends AS f, friends AS o "
                + "WHERE u.user_id = f.friend_id "
                + "AND u.user_id = o.friend_id "
                + "AND f.user_id= ? "
                + "AND o.user_id=?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> userDbStorage.makeUser(rs), userId, otherUserId);
    }
}
