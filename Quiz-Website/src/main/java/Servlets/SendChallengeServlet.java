package Servlets;

import DAOinterfaces.ChallengeDao;
import DAOinterfaces.UserDao;
import Objects.Challenge;
import Objects.User;

import javax.servlet.RequestDispatcher;
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
            if(receiver == null){
                httpServletRequest.setAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME, UserDao.ACCOUNT_NOT_FOUND);
            } else {
                long quizId = Long.parseLong(httpServletRequest.getParameter("quiz_id"));

                // Create a new Challenge object
                Challenge challenge = new Challenge(senderId, receiver.getId(), quizId);

                // Send the challenge using the DAO
                boolean success = challengeDao.sendChallenge(challenge);

                if (success) {
                    httpServletRequest.setAttribute("quiz_id", quizId);
                    httpServletRequest.setAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME, UserDao.ACCOUNT_FOUND_BY_NAME);
                } else {
                    httpServletRequest.setAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME, UserDao.SERVER_ERROR);
                }
            }


        } catch (NumberFormatException e) {
            httpServletResponse.getWriter().println("Invalid input. Please enter valid numeric IDs.");
        }

        RequestDispatcher rd = httpServletRequest.getRequestDispatcher("sendChallenge.jsp");
        rd.forward(httpServletRequest, httpServletResponse);
    }

}