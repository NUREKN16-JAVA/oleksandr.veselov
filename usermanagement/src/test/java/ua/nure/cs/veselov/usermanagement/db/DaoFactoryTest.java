package ua.nure.cs.veselov.usermanagement.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class DaoFactoryTest {

    @Test
    public void testGetUserDAO() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            UserDAO userDAO = daoFactory.getUserDAO();
            assertNotNull("UserDao instance is null", userDAO);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
