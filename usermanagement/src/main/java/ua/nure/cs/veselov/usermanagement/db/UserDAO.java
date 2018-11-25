package ua.nure.cs.veselov.usermanagement.db;

import java.util.Collection;

import ua.nure.cs.veselov.usermanagement.User;

public interface UserDAO {
    /**
     * Add new user to database
     * @param user with null id
     * @return user with auto generated id
     *         throw DatabaseException in any error occurs with DB
     * @author Alexander
     */
    User create(final User user) throws DatabaseException;
    
    User find(final Long id) throws DatabaseException;
    
    Collection<User> findall() throws DatabaseException;
    
    void update(final User user) throws DatabaseException;
    
    void delete(final User user) throws DatabaseException;
    
    void setConnectionFactory(final ConnectionFactory connectionFactory);
}
