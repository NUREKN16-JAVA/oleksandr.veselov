package ua.nure.cs.veselov.usermanagement.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DaoFactory;
import ua.nure.cs.veselov.usermanagement.db.DatabaseException;

public class EditServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("okButton") != null) {
            doOk(request, response);
        } else if (request.getParameter("cancelButton") != null) {
            doCancel(request, response);
        } else {
            showPage(request, response);
        }
    }
    
    private void doOk(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        try {
            user = getUser(request);
        } catch (ValidationException e) {
            request.setAttribute("error", e.getMessage());
            showPage(request, response);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            new ServletException(e);
        }
        request.getRequestDispatcher("/browse").forward(request, response);
    }

    private User getUser(HttpServletRequest request) throws ValidationException {
        User user = new User();
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String dateStr = request.getParameter("date");

        if (firstName == null) {
            throw new ValidationException("First name is empty");            
        }
        if (lastName == null) {
            throw new ValidationException("Last name is empty");            
        }
        if (dateStr == null) {
            throw new ValidationException("Date is empty");            
        }
        if (id != null) {
            user.setId(new Long(id));            
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
        } catch (ParseException e) {
            throw new ValidationException("Date is incorrect");
        }      

        return user;
    }

    protected void processUser(User user) throws DatabaseException {        
        DaoFactory.getInstance().getUserDAO().update(user);
    }

    private void doCancel(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/browse").forward(request, response);
        
    }

    protected void showPage(HttpServletRequest request, 
                            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/edit.jsp").forward(request, response);        
    }

}
