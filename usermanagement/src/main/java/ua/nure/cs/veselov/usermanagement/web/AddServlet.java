package ua.nure.cs.veselov.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DaoFactory;
import ua.nure.cs.veselov.usermanagement.db.DatabaseException;

public class AddServlet extends EditServlet {

    public AddServlet() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDAO().create(user);
    }

    @Override
    protected void showPage(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add.jsp").forward(request, response);
    }
}
