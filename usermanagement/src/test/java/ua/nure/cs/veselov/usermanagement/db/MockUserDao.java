package ua.nure.cs.veselov.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.cs.veselov.usermanagement.User;

public class MockUserDao implements UserDAO {

    private Long id = 0L;
    private Map<Long, User> users = new HashMap<>();
    
    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return (User) users.get(id);
    }

    @Override
    public Collection<User> findall() throws DatabaseException {
        return users.values();
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection find(String firstName, String lastName) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

}
