package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

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
        return users.get(id);
    }

    @Override
    public User create(User user ){
        user.setId(++lastId);
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public User update(User user){
        return user;
    }

    @Override
    public boolean delete(long id){
        return users.remove(id,users.get(id));
    }
}
