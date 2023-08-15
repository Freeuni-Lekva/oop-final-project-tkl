package Servlets;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizScoresDao;
import Objects.Questions.Question;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "startQuizServlet", value = "/startQuiz")
public class StartQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionsDao questionsSQL = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);
        QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);

        String quizIdString = request.getParameter("quiz_id");
        long quizId = Long.parseLong(quizIdString);

        // Calculate time user took to finish the quiz
        String startTimeString = request.getParameter("start_time");

        long startTime = Long.parseLong(startTimeString);

        List<Question> questions = questionsSQL.getQuizQuestions(quizId);

        // Process submitted answers
        double score = 0;
        for (Question question : questions) {
            String userAnswer = request.getParameter(String.valueOf(question.getId()));
            if(question.checkAnswer(userAnswer))
                score++;
        }

        long userId = Long.parseLong((String) request.getSession().getAttribute("MainUserID"));
        quizScoresDao.addNewScore(userId, quizId, score, startTime);

        // Redirect to a suitable page after submitting answers
        response.sendRedirect("quizResult.jsp?quiz_id=" + quizId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET request if needed
    }
}
