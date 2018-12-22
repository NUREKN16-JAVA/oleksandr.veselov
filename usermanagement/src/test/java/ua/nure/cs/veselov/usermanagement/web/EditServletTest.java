package ua.nure.cs.veselov.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.cs.veselov.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    @Test
    public void testEdit() {
        User user = new User(new Long(1000), "Alexander", "Veselov", new Date());
        getMockUserDao().expect("update", user);
        
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "Alexander");
        addRequestParameter("lastName", "Veselov");
        addRequestParameter("date", DateFormat.getDateInstance().format(user.getDateOfBirth()));
        addRequestParameter("okButton", "Ok");
        doPost();
    }
    
    @Test
    public void testEditEmptyFirstName() {
        addRequestParameter("id", "1000");
        addRequestParameter("lastName", "Veselov");
        addRequestParameter("date", DateFormat.getDateInstance().format(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().
                                       getMockRequest().
                                       getAttribute("error");
        assertNotNull("Error message is null", errorMessage);
        assertSame("First name is empty", errorMessage);
    }
    
    @Test
    public void testEditEmptyLastName() {
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "Alexander");
        addRequestParameter("date", DateFormat.getDateInstance().format(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().
                                       getMockRequest().
                                       getAttribute("error");
        assertNotNull("Error message is null", errorMessage);
        assertSame("Last name is empty", errorMessage);
    }

    @Test
    public void testEditEmptyDate() {
        addRequestParameter("id", "1000");
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

    
    @Test
    public void testEditEmptyDateIncorrect() {
        addRequestParameter("id", "1000");
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
}
