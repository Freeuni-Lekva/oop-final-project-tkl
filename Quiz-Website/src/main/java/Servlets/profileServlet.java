package Servlets;

import DAOinterfaces.UserDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;

@WebServlet(name = "profileServlet", value = "/profile")
public class profileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the UserDao instance from the ServletContext
        UserDao userDAO = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

        // Retrieve the username from the request parameter
        String userName = request.getParameter("name");

        // If the username is null and the MainUserName attribute is present in the session, use it as the user name
        if(userName == null && request.getSession().getAttribute("MainUserName") != null){
            userName = (String) request.getSession().getAttribute("MainUserName");
        }

        // Get the User object for the specified username from the UserDao
        User user = userDAO.getUserByName(userName);

        // Set the "profileUser" attribute in the session
        request.getSession().setAttribute("profileUser", user);

        // Forward the request and response to the "profile.jsp" page
        RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation in the POST method for this servlet
    }
}
