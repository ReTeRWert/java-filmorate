package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Service
public class InMemoryFriendshipStorage implements FriendshipStorage {

    private final UserStorage userStorage;

    @Autowired
    public InMemoryFriendshipStorage(UserStorage userStorage){
        this.userStorage = userStorage;
    }


}
