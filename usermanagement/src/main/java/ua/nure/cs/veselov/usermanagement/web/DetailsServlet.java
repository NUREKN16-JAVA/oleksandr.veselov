package ua.nure.cs.veselov.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends EditServlet {

    public DetailsServlet() {
        // TODO Auto-generated constructor stub
    }
    
    protected void showPage(HttpServletRequest request, 
                            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/details.jsp").forward(request, response);        
    }

}
