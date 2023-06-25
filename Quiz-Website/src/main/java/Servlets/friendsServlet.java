package Servlets;

import DAOinterfaces.FriendsDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "friendsServlet", value = "/friends")

public class FriendsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the FriendsDao instance from the ServletContext
        FriendsDao friendsDao = (FriendsDao) request.getServletContext().getAttribute(FriendsDao.ATTRIBUTE_NAME);

        // Retrieve the profile username from the request parameter
        String profile_user = (String) request.getParameter("name");

        // Get the list of friends for the specified profile user
        List<User> friends = friendsDao.getUserFriends(profile_user);

        // Set the "user_friends" attribute in the session
        request.getSession().setAttribute("user_friends", friends);
        request.getSession().setAttribute("profileUser", profile_user);

        // Forward the request and response to the "friends.jsp" page
        RequestDispatcher rd = request.getRequestDispatcher("friends.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation in the POST method for this servlet
    }
}
