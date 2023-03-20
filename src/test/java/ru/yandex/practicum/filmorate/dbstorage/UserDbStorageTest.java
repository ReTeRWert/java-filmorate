package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {

    private final UserDbStorage userDbStorage;


    private void addUser() {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("usertest");
        user.setName("Name user test");
        user.setBirthday(LocalDate.of(1957, 6, 14));

        userDbStorage.addUser(user);
    }

    private void removeUser() {
        userDbStorage.removeUserById(1);
    }

    @Test
    void getById() {
        User user = userDbStorage.getUserById(1);

        assertEquals("newmail@mail.ru", user.getEmail());

        Optional<User> userOptional = Optional.of(user);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void getUsers() {
        List<User> users = userDbStorage.getUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    void updateUser() {
        addUser();
        User user = userDbStorage.getUserById(1);
        user.setName("new name");
        user.setEmail("newmail@mail.ru");

        userDbStorage.updateUser(user);

        User updateUser = userDbStorage.getUserById(1);

        assertEquals("new name", updateUser.getName());
        assertEquals("newmail@mail.ru", updateUser.getEmail());
    }
}
