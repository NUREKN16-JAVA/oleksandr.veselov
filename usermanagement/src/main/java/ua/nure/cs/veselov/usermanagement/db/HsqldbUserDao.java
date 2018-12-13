package ua.nure.cs.veselov.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.cs.veselov.usermanagement.User;

class HsqldbUserDao implements UserDAO {
    private static final String INSERT_QUERY = "INSERT INTO users(firstname, lastname, dateofbirth) VALUES(?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    private static final String FINDALL_QUERY = "SELECT * FROM users";
    private static final String FIND_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String IDENTITY_CALL = "call IDENTITY()";
    private ConnectionFactory connectionFactory;

    public ConnectionFactory getConnectionFacory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFacory) {
        this.connectionFactory = connectionFacory;
    }

    public HsqldbUserDao() {
        // TODO Auto-generated constructor stub
    }
    
    public HsqldbUserDao(ConnectionFactory connectionFacory) {
        this.connectionFactory = connectionFacory;
    }

    @Override
    public User create(final User user) throws DatabaseException {  
        try {
            Connection connection = connectionFactory.createConnection();
            
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY);
            
            insertStatement.setString(1, user.getFirstName());
            insertStatement.setString(2, user.getLastName());
            insertStatement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            
            int createCount = insertStatement.executeUpdate();
            if (createCount != 1)
            {
                throw new DatabaseException("Number of inserted rows: " + createCount);
            }
            
            CallableStatement identityStatement = connection.prepareCall(IDENTITY_CALL);
            ResultSet keys = identityStatement.executeQuery();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
            
            keys.close();
            identityStatement.close();
            insertStatement.close();
            connection.close();
            
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User find(final Long id) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement findStatement = connection.prepareStatement(FIND_QUERY);
            findStatement.setLong(1, id.longValue());
            ResultSet usersSet = findStatement.executeQuery();
            if (!usersSet.next()) {
                throw new DatabaseException("Wrong user id=" + id);
            }
            User user = new User();
            user.setId(new Long(usersSet.getLong(1)));
            user.setFirstName(usersSet.getString(2));
            user.setLastName(usersSet.getString(3));
            user.setDateOfBirth(usersSet.getDate(4));
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Collection<User> findall() throws DatabaseException {
        try {
            Collection<User> users = new LinkedList<User>();
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet usersSet = statement.executeQuery(FINDALL_QUERY);
            while (usersSet.next()) {
                User user = new User();
                user.setId(new Long(usersSet.getLong(1)));
                user.setFirstName(usersSet.getString(2));
                user.setLastName(usersSet.getString(3));
                user.setDateOfBirth(usersSet.getDate(4));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(final User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY);
            updateStatement.setString(1, user.getFirstName());
            updateStatement.setString(2, user.getLastName());
            updateStatement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            updateStatement.setLong(4, user.getId().longValue());
            
            int updateCount = updateStatement.executeUpdate();
            if (updateCount != 1) {
                throw new DatabaseException("Number of the updated rows: " + updateCount);
            }
            
            updateStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(final User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY);
            deleteStatement.setLong(1, user.getId().longValue());
            int deleteCount = deleteStatement.executeUpdate();
            if (deleteCount != 1) {
                throw new DatabaseException("Number of the deleted rows: " + deleteCount);
            }
            deleteStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
