package Servlets;

import DAOinterfaces.FriendRequestDao;
import DAOinterfaces.FriendsDao;
import Objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "removeFriendServlet", value = "/remove_friend")
public class removeFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation in the GET method for this servlet
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the FriendsDao instance from the ServletContext
        FriendsDao friendsDao = (FriendsDao) request.getServletContext().getAttribute(FriendsDao.ATTRIBUTE_NAME);

        // Retrieve the friend's name and the main user's name from the request parameters
        String friend_name = (String) request.getParameter("friend_name");
        String mainUser = (String) request.getSession().getAttribute("MainUserName");

        // Remove the friendship between the friend and the main user using the FriendsDao
        friendsDao.removeFriendship(friend_name, mainUser);

        // Redirect the user to the friends page for the main user
        response.sendRedirect("/friends?name=" + mainUser);
    }
}
