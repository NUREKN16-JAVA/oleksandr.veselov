package ua.nure.cs.veselov.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
    
    private static final String DAO_FACTORY = "dao.Factory";
    protected static final String USER_DAO = "dao.UserDAO";
    protected static Properties properties;
    
    private static DaoFactory instance;   
    
    protected DaoFactory() { 
        
    }
    
    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void initialize(Properties properties) {
        DaoFactory.properties = properties;
        instance = null;
    }
    
    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            try {
                Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
    
    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImplementation(properties);
    }
    
    public abstract UserDAO getUserDAO();
}
