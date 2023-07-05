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
import java.io.IOException;
import java.util.List;

@WebServlet(name = "friendRequestServlet", value = "/friend_request")
public class friendRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the UserDao and FriendRequestDao instances from the ServletContext
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);

        // Retrieve the username from the request parameter
        String id = request.getParameter("id");
        Long user_id = Long.parseLong(id);

        // Get the list of received friend requests for the specified user
        List<User> friend_requests = friendRequestDao.getReceivedFriendRequests(user_id);
        // Set the "friend_requests" attribute in the session
        request.getSession().setAttribute("friend_requests", friend_requests);

        // Forward the request and response to the "friendRequest.jsp" page
        RequestDispatcher rd = request.getRequestDispatcher("friendRequest.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the FriendsDao and FriendRequestDao instances from the ServletContext
        FriendsDao friendsDao = (FriendsDao) request.getServletContext().getAttribute(FriendsDao.ATTRIBUTE_NAME);
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);

        // Retrieve the values from the request parameters
        String accept = (String) request.getParameter("accept");
        String id = (String) request.getParameter("friendID");

        String main_user_id = (String) request.getSession().getAttribute("main_user_id");

        if (id != null) {
            Long friend_id = Long.parseLong(id);
            // Check if the request is accepted
            if (accept.equals("true")) {
                // If accepted, add the friendship between the main user and the request user
                friendsDao.addFriendship(Long.parseLong(main_user_id), friend_id);
            }
            // Remove the friend request between the main user and the request user
            friendRequestDao.removeFriendRequest(Long.parseLong(main_user_id), friend_id);
            friendRequestDao.removeFriendRequest(friend_id, Long.parseLong(main_user_id));
            // Redirect the user to the "friend_request" page for the main user
        }

        response.sendRedirect("/friend_request?id=" + main_user_id);
    }
}
