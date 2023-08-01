package Servlets;

import DAOinterfaces.FriendRequestDao;
import Objects.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FriendRequestCountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the UserDao and FriendRequestDao instances from the ServletContext
        FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);

        String id = (String) request.getSession().getAttribute("MainUserID");
        List<User> friend_requests = (List<User>) request.getSession().getAttribute("friend_requests");

        if (id != null){
            Long user_id = Long.parseLong(id);
            // Get the list of received friend requests for the specified user
            friend_requests = friendRequestDao.getReceivedFriendRequests(user_id);
            // Set the "friend_requests" attribute in the session
            request.getSession().setAttribute("friend_requests", friend_requests);
        }

        int friendRequestsCount = getFriendRequestsCount(request);
        // Set the response content type to JSON
        response.setContentType("application/json");

        // Create a JSON string with the friend requests count
        String json = "{\"count\":" + friendRequestsCount + "}";

        // Send the JSON response to the client
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
        }
    }
    private int getFriendRequestsCount(HttpServletRequest request) {
        List<User> friend_requests = (List<User>) request.getSession().getAttribute("friend_requests");
        int numFriendRequests = (friend_requests == null) ? 0 : friend_requests.size();
        return numFriendRequests;
    }
}
