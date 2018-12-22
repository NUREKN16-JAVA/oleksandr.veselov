package ua.nure.cs.veselov.usermanagement.web;

import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.cs.veselov.usermanagement.User;

public class BrowseServletTest extends MockServletTestCase {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    @Test
    public void testBrowse() {
        User user = new User(1000L, "Alexander", "Veselov", new Date());
        List<User> list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findall", list);
        doGet();
        Collection collection = (Collection) getWebMockObjectFactory().
                                             getMockSession().
                                             getAttribute("users");
        assertNotNull(collection);
        assertSame(list, collection);
    }

    @Test
    public void testEdit() {
        User user = new User(1000L, "Alexander", "Veselov", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().
                                    getMockSession().
                                    getAttribute("user");
        assertNotNull(userInSession);
        assertSame(user, userInSession);
    }
    
    @Test
    public void testEditWithoutUserSelection() {
        addRequestParameter("editButton", "Edit");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }
    
    @Test
    public void testDelete() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        getMockUserDao().expect("delete", user);
        addRequestParameter("deleteButton", "Delete");
        addRequestParameter("id", "1000");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("message"));
    }
    
    @Test
    public void testDeleteWithoutSelection() {
        addRequestParameter("deleteButton", "Delete");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }

    @Test
    public void testDetails() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("detailsButton", "Details");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory()
                .getMockSession().getAttribute("user");
        assertNotNull(userInSession);
        assertSame(user, userInSession);
    }
    
    @Test
    public void testDetailsWithoutSelection() {
        addRequestParameter("detailsButton", "Details");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }
}
