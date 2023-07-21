package Servlets;

import DAOinterfaces.ChallengeDao;
import DAOinterfaces.UserDao;
import Objects.Challenge;
import Objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "sendChallengeServlet", value = "/sendChallenge")
public class SendChallengeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao userDao = (UserDao) httpServletRequest.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
        ChallengeDao challengeDao = (ChallengeDao) httpServletRequest.getServletContext().getAttribute(ChallengeDao.ATTRIBUTE_NAME);

        try {
            // Retrieve parameters from the form
            long senderId = Long.parseLong((String) httpServletRequest.getSession().getAttribute("main_user_id"));
            String receiverUsername = httpServletRequest.getParameter("receiverUsername");
            User receiver = userDao.getUserByName(receiverUsername);
            long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));

            // Create a new Challenge object
            Challenge challenge = new Challenge(senderId, receiver.getId(), quizId);

            // Send the challenge using the DAO
            boolean success = challengeDao.sendChallenge(challenge);

            if (success) {
                httpServletResponse.getWriter().println("Challenge sent successfully!");
            } else {
                httpServletResponse.getWriter().println("Failed to send the challenge.");
            }
        } catch (NumberFormatException e) {
            httpServletResponse.getWriter().println("Invalid input. Please enter valid numeric IDs.");
        }
    }

}