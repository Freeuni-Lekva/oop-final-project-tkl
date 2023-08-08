package Servlets;

import DAOinterfaces.FriendsDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "removeFriendServlet", value = "/remove_friend")
public class RemoveFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation in the GET method for this servlet
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the FriendsDao instance from the ServletContext
        FriendsDao friendsDao = (FriendsDao) request.getServletContext().getAttribute(FriendsDao.ATTRIBUTE_NAME);

        // Retrieve the friend's name and the main user's name from the request parameters
        String id = (String) request.getParameter("friend_id");
        String main_user_id = (String) request.getSession().getAttribute("MainUserID");

        // Remove the friendship between the friend and the main user using the FriendsDao
        if(id != null) friendsDao.removeFriendship(Long.parseLong(id), Long.parseLong(main_user_id));

        // Redirect the user to the friends page for the main user
        response.sendRedirect("/friends?id=" + main_user_id);
    }
}
