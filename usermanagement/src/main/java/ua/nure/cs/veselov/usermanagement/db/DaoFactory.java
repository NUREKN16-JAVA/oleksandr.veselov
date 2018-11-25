package ua.nure.cs.veselov.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    
    private static final String USER_DAO = "dao.UserDAO";
    private static final DaoFactory INSTANCE = new DaoFactory();   
    private static final Properties properties;
    
    private DaoFactory() { 
        
    }
    
    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static DaoFactory getInstance() {
        return INSTANCE;
    }
    
    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImplementation(properties);
    }
    
    public UserDAO getUserDAO() {
        UserDAO userDAO = null;
        try {
            Class clazz = Class.forName(properties.getProperty(USER_DAO));
            userDAO = (UserDAO) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDAO;
    }
}
