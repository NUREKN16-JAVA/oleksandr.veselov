package ua.nure.cs.veselov.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.cs.veselov.usermanagement.User;

public class AddServletTest extends MockServletTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    @Test
    public void testAdd() {
        Date date = new Date();
        User newUser = new User("Alexander", "Veselov", date);
        User user = new User(new Long(1000), "Alexander", "Veselov", date);
        getMockUserDao().expectAndReturn("create", newUser, user);
        
        addRequestParameter("firstName", "Alexander");
        addRequestParameter("lastName", "Veselov");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }
    
    @Test
    public void testAddEmptyLastName() {
        Date date = new Date();
        addRequestParameter("firstName", "Alexander");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().
                                       getMockRequest().
                                       getAttribute("error");
        assertNotNull("Error message is null", errorMessage);
        assertSame("Last name is empty", errorMessage);
    }
    
    @Test
    public void testAddEmptyDateIncorrect() {
        addRequestParameter("firstName", "Alexander");
        addRequestParameter("lastName", "Veselov");
        addRequestParameter("date", "Life is prison. You are free.");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().
                                       getMockRequest().
                                       getAttribute("error");
        assertNotNull("Error message is null", errorMessage);
        assertSame("Date is incorrect", errorMessage);
    }
    
    @Test
    public void testAddEmptyFirstName() {
        Date date = new Date();
        addRequestParameter("lastName", "Veselov");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().
                                       getMockRequest().
                                       getAttribute("error");
        assertNotNull("Error message is null", errorMessage);
        assertSame("First name is empty", errorMessage);        
    }

    @Test
    public void testAddEmptyDate() {
        addRequestParameter("firstName", "Alexander");
        addRequestParameter("lastName", "Veselov");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().
                                       getMockRequest().
                                       getAttribute("error");
        assertNotNull("Error message is null", errorMessage);
        assertSame("Date is empty", errorMessage);
    }
}
