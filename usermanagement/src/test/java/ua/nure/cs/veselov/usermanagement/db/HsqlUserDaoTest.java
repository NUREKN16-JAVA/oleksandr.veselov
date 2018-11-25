package ua.nure.cs.veselov.usermanagement.db;
import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;

import ua.nure.cs.veselov.usermanagement.User;

public class HsqlUserDaoTest extends DatabaseTestCase {
    
    private HsqldbUserDao dao = new HsqldbUserDao();
    private ConnectionFactory connectionFactory ;

    private static final String CREATE_USER_LASTNAME = "Veselov";
    private static final String CREATE_USER_FIRSTNAME = "Alexander";
    
    private static final Long FIND_USER_ID = 1000L;
    private static final String FIND_USER_FIRSTNAME = "Bill";
    private static final String FIND_USER_LASTNAME = "Gates";
    
    private static final Long DELETE_USER_ID = 1001L;
    
    private static final String UPDATE_USER_FIRSTNAME = "Steve";
    private static final String UPDATE_USER_LASTNAME = "Jobs";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao.setConnectionFactory(connectionFactory);
    }

    @Test
    public void testCreate() throws DatabaseException {
        User user = new User();
        user.setFirstName(CREATE_USER_FIRSTNAME);
        user.setLastName(CREATE_USER_LASTNAME);
        user.setDateOfBirth(new Date());
        assertNull(user.getId());
        User createdUser = dao.create(user);
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
    }
    
    @Test
    public void testFindAll() throws DatabaseException {
        Collection<User> collection = dao.findall();
        assertNotNull("Collection is null", collection);
        assertEquals("Collection size.", 2, collection.size());
    }
    
    @Test
    public void testFind() throws DatabaseException {
        User user = dao.find(FIND_USER_ID);
        assertNotNull(user);
        assertEquals(FIND_USER_FIRSTNAME, user.getFirstName());
        assertEquals(FIND_USER_LASTNAME, user.getLastName());
    }
    
    @Test
    public void testDelete() {
        User user = new User();
        user.setId(DELETE_USER_ID);
        try {
            dao.delete(user);
            dao.find(DELETE_USER_ID); // если пользователь не будет найден,
                                      // то произойдет исключение, которое не позволит выполнить
                                      // следующий фрагмент кода
            fail(); // иначе - ошибка
        } catch (DatabaseException e) {
            assert(e.getMessage().contains(DELETE_USER_ID.toString()));
        }
    }
    
    @Test
    public void testUpdate() throws DatabaseException {
        User user = dao.find(FIND_USER_ID);
        assertNotNull(user);
        user.setFirstName(UPDATE_USER_FIRSTNAME);
        user.setLastName(UPDATE_USER_LASTNAME);
        dao.update(user);
        
        User updatedUser = dao.find(FIND_USER_ID);
        assertEquals(UPDATE_USER_FIRSTNAME, updatedUser.getFirstName());
        assertEquals(UPDATE_USER_LASTNAME, updatedUser.getLastName());
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = DaoFactory.getInstance().getConnectionFactory();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }
}
