package ua.nure.cs.veselov.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DaoFactory;
import ua.nure.cs.veselov.usermanagement.db.MockDaoFactory;
import ua.nure.cs.veselov.usermanagement.util.Messages;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;

    private Mock mockUserDao;

    private List<User> users;
    
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        try {
            Properties properties = new Properties();
            DaoFactory.initialize(properties);
            properties.setProperty("dao.Factory", MockDaoFactory.class.getName());
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            User expectedUser = new User(new Long(1488), "Jimmy", "Page", new Date());
            users = Collections.singletonList(expectedUser);
            mockUserDao.expectAndReturn("findall", users);
            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setVisible(true);
    }

    @After
    protected void tearDown() throws Exception {
        mockUserDao.verify();
        mainFrame.setVisible(false);
        TestHelper.cleanUp(this);
        super.tearDown();
    }
    
    private Component find(Class<?> componentClass, String name) {
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + "'", component);
        return component;
    }
    
    @Test
    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
        assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
        assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));

        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");    
    }
    
    private void fillField(String firstName, String lastName, Date now) {
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

        getHelper().sendString(
                new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(
                new StringEventData(this, lastNameField, lastName));
        DateFormat formatter = DateFormat.getDateInstance();
        String date = formatter.format(now);
        getHelper().sendString(
                new StringEventData(this, dateOfBirthField, date));
    }
    
    @Test
    public void testAddUser() {
        try {
            String firstName = "Alexander";
            String lastName = "Veselov";
            Date now = new Date();

            User user = new User(firstName, lastName, now);
            
            User expectedUser = new User(new Long(1), firstName, lastName, now);
            mockUserDao.expectAndReturn("create", user, expectedUser);
            
            ArrayList<User> users = new ArrayList<>(this.users);
            users.add(expectedUser);
            mockUserDao.expectAndReturn("findall", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            fillField(firstName, lastName, now);

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(2, table.getRowCount());
            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

}
