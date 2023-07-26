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

@WebServlet(name = "addFriendServlet", value = "/add_friend")
public class AddFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the required DAO instances from the ServletContext
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);
        FriendsDao friendsDao = (FriendsDao) request.getServletContext().getAttribute(FriendsDao.ATTRIBUTE_NAME);

        // Retrieve the main user and profile user from the session
        User profileUser = (User) request.getSession().getAttribute("profileUser");
        String main_user_id = (String) request.getSession().getAttribute("MainUserID");

        // Set the "addButton?" attribute based on the conditions
        request.getSession().setAttribute("addButton?", false);
        if(Long.parseLong(main_user_id) != profileUser.getId()){
            if(!friendRequestDao.checkFriendRequest(Long.parseLong(main_user_id), profileUser.getId())
                    && !friendsDao.checkIfFriends(Long.parseLong(main_user_id), profileUser.getId()) &&
                    !friendRequestDao.checkFriendRequest(profileUser.getId(), Long.parseLong(main_user_id))){

                request.getSession().setAttribute("addButton?", true);
            }
        }
        System.out.println("profileUserid: " + profileUser.getId());
        System.out.println("mainId: " + main_user_id);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the FriendRequestDao instance from the ServletContext
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);

        // Retrieve the main user and profile user from the session
        String main_user_id = (String) request.getSession().getAttribute("MainUserID");
        User profileUser = (User) request.getSession().getAttribute("profileUser");

        // Add a friend request from the main user to the profile user
        if(main_user_id != null) friendRequestDao.addFriendRequest(Long.parseLong(main_user_id), profileUser.getId());

        // Redirect to the profile page of the profile user
        response.sendRedirect("/profile?id=" + profileUser.getId());
    }
}
