package ua.nure.cs.veselov.usermanagement.db;

public class DaoFactoryImplementation extends DaoFactory {


    @Override
    public UserDAO getUserDAO() {
        UserDAO userDAO = null;
        try {
            Class clazz = Class.forName(properties.getProperty(USER_DAO));
            userDAO = (UserDAO) clazz.newInstance();
            userDAO.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDAO;
    }

}
