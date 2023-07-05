package Servlets;

import DAOinterfaces.UserDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "editProfileServlet", value = "/edit_user")
public class editProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the UserDao instance from the ServletContext
        UserDao userDAO = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

        // Retrieve the id from the request parameter
        String id =  request.getParameter("id");
        Long user_id = Long.parseLong(id);

        // Retrieve the user object from the UserDao based on the username
        User user = userDAO.getUserById(user_id);

        // Set the "MainUser" attribute in the session
        request.getSession().setAttribute("mainUser", user);

        // Forward the request and response to the "editUser.jsp" page
        RequestDispatcher rd = request.getRequestDispatcher("editUser.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the updated values from the request parameters
        String newFirstName = request.getParameter("realName");
        String newLastName = request.getParameter("lastName");
        String newImagePath = request.getParameter("imagePath");
        String newDescription = request.getParameter("description");

        // Retrieve the UserDao instance from the ServletContext
        UserDao userDAO = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
        // Retrieve the username from the request parameter
        String id = request.getParameter("id");
        Long user_id = Long.parseLong(id);

        // Update the user information using the UserDao
        userDAO.changeRealName(user_id, newFirstName);
        userDAO.changeRealLastName(user_id, newLastName);
        userDAO.changeImagePath(user_id, newImagePath);
        userDAO.changeDescription(user_id, newDescription);

        // Redirect the user to the "profile" page
        response.sendRedirect("/profile");
    }
}
