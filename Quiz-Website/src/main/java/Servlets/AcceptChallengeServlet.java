package Servlets;

import DAOinterfaces.ChallengeDao;
import DAOinterfaces.QuizDao;
import Objects.Challenge;
import Objects.Quiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "acceptChallengeServlet", value = "/acceptChallenge")
public class AcceptChallengeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long challengeId = Long.parseLong(request.getParameter("challenge_id"));
        ChallengeDao challengeDao = (ChallengeDao) request.getServletContext().getAttribute(ChallengeDao.ATTRIBUTE_NAME);
        Challenge challenge = challengeDao.getChallenge(challengeId);
        boolean success = challengeDao.removeChallenge(challengeId);
        long quizId = challenge.getQuizId();
        if(success){
            request.getSession().setAttribute("quiz_id", Long.toString(quizId));
        } else {
            response.getWriter().println("Encountered some kind of error :(((");
        }
        RequestDispatcher rd = request.getRequestDispatcher("quiz.jsp");
        rd.forward(request, response);
    }
}
