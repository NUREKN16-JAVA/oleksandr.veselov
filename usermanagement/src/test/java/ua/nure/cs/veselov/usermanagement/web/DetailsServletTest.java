package ua.nure.cs.veselov.usermanagement.web;

import java.util.Collection;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.cs.veselov.usermanagement.User;

public class DetailsServletTest extends MockServletTestCase {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    @Test
    public void testOkButton() {
        addRequestParameter("okButton", "Ok");
        doPost();
        assertNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }

}
