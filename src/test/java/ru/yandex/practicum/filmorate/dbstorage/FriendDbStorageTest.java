package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendDbStorageTest {

    private final UserDbStorage userDbStorage;
    private final FriendDbStorage friendDbStorage;


    private void addUsers() {

        User user2 = new User();
        user2.setEmail("friend@mail.ru");
        user2.setLogin("friendtest");
        user2.setName("Name friend test");
        user2.setBirthday(LocalDate.of(1958, 7, 21));

        userDbStorage.addUser(user2);
    }

    private void removeUsers() {

        for (int i = 0; i < userDbStorage.getUsers().size(); i++) {
            userDbStorage.removeUserById(i);
        }
    }

    @Test
    void addFriend() {
        friendDbStorage.addFriend(1, 2);

        List<User> users = userDbStorage.getUsers();
        List<User> friend = friendDbStorage.getFriendsUser(1);

        assertNotNull(friend);
        assertEquals(1, friend.size());
        assertEquals("friendtest", friend.get(0).getLogin());
    }

    @Test
    void removeFriend() {

        friendDbStorage.removeFriend(1, 2);

        List<User> friendEmpty = friendDbStorage.getFriendsUser(1);

        assertEquals(0, friendEmpty.size());
    }

    @Test
    void getCommonFriends() {
        addUsers();
        User user3 = new User();
        user3.setEmail("commonfriend@mail.ru");
        user3.setLogin("commonfriendtest");
        user3.setName("Name friend test");
        user3.setBirthday(LocalDate.of(1960, 9, 17));

        userDbStorage.addUser(user3);

        List<User> users = userDbStorage.getUsers();

        friendDbStorage.addFriend(1, 3);
        friendDbStorage.addFriend(2, 3);

        List<User> commonFriend = friendDbStorage.getCommonFriends(1, 2);

        assertEquals(1, commonFriend.size());
        assertEquals("commonfriendtest", commonFriend.get(0).getLogin());

        friendDbStorage.removeFriend(1, 3);
        friendDbStorage.removeFriend(2, 3);
    }

}
