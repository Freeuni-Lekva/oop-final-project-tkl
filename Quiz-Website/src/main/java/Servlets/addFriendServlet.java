package Servlets;

import DAOinterfaces.FriendRequestDao;
import DAOinterfaces.FriendsDao;
import DAOinterfaces.UserDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "addFriendServlet", value = "/add_friend")
public class addFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the required DAO instances from the ServletContext
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);
        FriendsDao friendsDao = (FriendsDao) request.getServletContext().getAttribute(FriendsDao.ATTRIBUTE_NAME);

        // Retrieve the main user and profile user from the session
        String mainUser = (String) request.getSession().getAttribute("MainUserName");
        User profileUser = (User) request.getSession().getAttribute("profileUser");

        // Set the "addButton?" attribute based on the conditions
        request.getSession().setAttribute("addButton?", false);
        if(!mainUser.equals(profileUser.getName())){
            if(!friendRequestDao.checkFriendRequest(mainUser, profileUser.getName())
                    && !friendsDao.checkIfFriends(mainUser, profileUser.getName()) &&
                    !friendRequestDao.checkFriendRequest(profileUser.getName(), mainUser)){

                request.getSession().setAttribute("addButton?", true);
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the FriendRequestDao instance from the ServletContext
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);

        // Retrieve the main user and profile user from the session
        String mainUser = (String) request.getSession().getAttribute("MainUserName");
        User profileUser = (User) request.getSession().getAttribute("profileUser");

        // Add a friend request from the main user to the profile user
        friendRequestDao.addFriendRequest(mainUser, profileUser.getName());

        // Redirect to the profile page of the profile user
        response.sendRedirect("/profile?name=" + profileUser.getName());
    }
}
