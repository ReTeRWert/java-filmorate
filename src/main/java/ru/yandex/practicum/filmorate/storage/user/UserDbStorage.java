package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Qualifier
@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public User addUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");

        Integer userId = jdbcInsert.executeAndReturnKey(toMap(user)).intValue();
        user.setId(userId);

        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users " +
                "SET email = ?, login = ?, user_name =?, birthday = ? " +
                "WHERE user_id =?";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());

        return user;
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    public User getUserById(Integer userId) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE user_id =?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId);
        if (users.size() != 1) {
            throw new NotFoundException("Такого пользователя не существует.");
        }
        return users.get(0);
    }

    public void removeUserById(Integer userId) {
        String sql = "DELETE FROM users " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public User makeUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("user_name"),
                rs.getDate("birthday").toLocalDate()
        );
    }

    private Map<String, Object> toMap(User user) {
        Map<String, Object> userParameters = new HashMap<>();

        userParameters.put("email", user.getEmail());
        userParameters.put("login", user.getLogin());
        userParameters.put("user_name", user.getName());
        userParameters.put("birthday", user.getBirthday());

        return userParameters;
    }
}
