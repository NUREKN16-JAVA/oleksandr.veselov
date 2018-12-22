package ua.nure.cs.veselov.usermanagement.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DaoFactory;
import ua.nure.cs.veselov.usermanagement.db.DatabaseException;
import ua.nure.cs.veselov.usermanagement.db.UserDAO;

public class BrowseServlet extends HttpServlet {

    public BrowseServlet() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("addButton") != null) {
            add(request, response);
        } else if (request.getParameter("editButton") != null) {
            edit(request, response);           
        } else if (request.getParameter("deleteButton") != null) {
            delete(request, response);
        } else if (request.getParameter("detailsButton") != null) {
            details(request, response);
        } else {
            browse(request, response);
        }
    }

    private void details(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().length() == 0) {
            request.setAttribute("error", "You have not selected a user.");
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
            return;
        }
        try {
            User user = (User) DaoFactory.getInstance().getUserDAO().find(new Long(id));
            request.getSession().setAttribute("user", user);
        } catch (Exception e) {
            request.setAttribute("error", e.toString());
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/details").forward(request, response);
    }

    private void delete(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().length() == 0) {
            request.setAttribute("error", "You have not selected a user.");
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
            return;
        }
        try {
            UserDAO userDao = DaoFactory.getInstance().getUserDAO();
            User user = (User) userDao.find(new Long(id));
            userDao.delete(user);
            request.setAttribute("message", "Deleted user: " + user.toString());    
            response.sendRedirect("./browse");            
        } catch (Exception e) {
            request.setAttribute("error", e.toString());
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
            return;
        }        
    }

    private void edit(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().length() == 0) {
            request.setAttribute("error", "You have not selected a user.");
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
            return;
        }
        try {
            User user = (User) DaoFactory.getInstance().getUserDAO().find(new Long(id));
            request.getSession().setAttribute("user", user);
        } catch (Exception e) {
            request.setAttribute("error", e.toString());
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/edit").forward(request, response);
    }

    private void add(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add").forward(request, response);
    }

    private void browse(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        Collection<User> users;
        try {
            users = DaoFactory.getInstance().getUserDAO().findall();
            request.getSession().setAttribute("users", users);
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }        
    }

}
