package Servlets;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizDao;
import DAOinterfaces.QuizScoresDao;
import Objects.Questions.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "onePageQuizServlet", value = "/startOnePageQuiz")
public class OnePageQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);

        String quizIdString = request.getParameter("quiz_id");
        long quizId = Long.parseLong(quizIdString);

        // Calculate time user took to finish the quiz
        String startTimeString = request.getParameter("start_time");

        long startTime = Long.parseLong(startTimeString);

        List<Question> questions = (List<Question>) request.getSession().getAttribute("CurrentQuizQuestions");

        // Process submitted answers
        double score = 0;
        List<String> answers = new ArrayList<>();
        for (Question question : questions) {
            String userAnswer = request.getParameter(String.valueOf(question.getId()));
            answers.add(userAnswer);
            if(question.checkAnswer(userAnswer))
                score++;
        }

        long userId = Long.parseLong((String) request.getSession().getAttribute("MainUserID"));

        long scoreId = quizScoresDao.addNewScore(userId, quizId, score, startTime);
        String answersParam = String.join(",", answers);

        // Redirect to a suitable page after submitting answers
        response.sendRedirect("quizResult.jsp?score_id=" + scoreId + "&quiz_id=" + quizId +
                "&answers=" + URLEncoder.encode(answersParam, "UTF-8"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //No implementation needed
    }
}
