package Servlets;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizDao;
import DAOinterfaces.QuizScoresDao;
import Objects.Questions.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name="multiplePageQuizServlet", value = "/startMultiplePageQuiz")
public class MultiplePageQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String quizIdString = request.getParameter("quiz_id");
        if(quizIdString == null){
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        // Retrieving all necessary information
        long quizId = Long.parseLong(quizIdString);
        int index = (int) request.getSession().getAttribute("CurrentQuizQuestionsIndex");
        List<Question> questions = (List<Question>) request.getSession().getAttribute("CurrentQuizQuestions");

        // Checking users answer
        String userAnswer = request.getParameter(String.valueOf(questions.get(index).getId()));
        Question currentQuestion = questions.get(index);
        boolean isCorrect = currentQuestion.checkAnswer(userAnswer);

        String answer = "<p>User's Answer:<p class=" + (isCorrect ? "\"result-correct\"" : "\"result-wrong\"") + ">" + userAnswer + "</p></p>\n" +
                "<p>Correct Answers: <p class=\"result-correct\">";

        for(String correctAnswer: currentQuestion.getCorrectAnswers()){
            answer += " " + correctAnswer;
        }
        answer += "</p></p>";

        // Setting attributes to session, so startMultiplePageQuiz page firstly shows correct answer and users answer
        request.getSession().setAttribute("CurrenQuizQuestionIsAnswered", "yee");
        request.getSession().setAttribute("CurrenQuizQuestionAnswer", answer);

        // Calculating score and update if it's necessary
        int score = (int) request.getSession().getAttribute("CurrentQuizScore");
        if(isCorrect){
            score++;
            request.getSession().setAttribute("CurrentQuizScore", score);
        }

        // Checking if user is on last question
        if(questions.size() == index + 1) {

            index --;
            // If user comes to that part of code, it means that user finished test, therefore code adds new score to DB
            request.getSession().setAttribute("CurrenQuizQuestionIsLast", "yee");
            long startTime = (long) request.getSession().getAttribute("CurrentQuizStartTime");;
            long userId = Long.parseLong((String) request.getSession().getAttribute("MainUserID"));
            QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);

            long scoreId = quizScoresDao.addNewScore(userId, quizId, score, startTime);
            request.getSession().setAttribute("CurrenQuizScoreID", scoreId);
        }

        // Forward users request
        request.getSession().setAttribute("CurrentQuizQuestionsIndex", index + 1);
        RequestDispatcher rd = request.getRequestDispatcher("startMultiplePageQuiz.jsp?quiz_id=" + quizId);
        rd.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String quizIdString = request.getParameter("quiz_id");

        if(quizIdString == null){
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        // Retrieving all necessary information from servlet context and DB
        long quizId = Long.parseLong(quizIdString);
        QuestionsDao questionsDao = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);
        QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
        List<Question> questions = questionsDao.getQuizQuestions(quizId, quizDao.getQuizById(quizId).isQuestionsSorted());

        // Setting attributes to session
        request.getSession().setAttribute("CurrentQuizQuestions", questions);
        request.getSession().setAttribute("CurrentQuizQuestionsIndex", 0);
        request.getSession().setAttribute("CurrentQuizStartTime", new Date().getTime());
        request.getSession().setAttribute("CurrentQuizScore", 0);

        // Redirect user to next page
        RequestDispatcher rd = request.getRequestDispatcher("startMultiplePageQuiz.jsp?quiz_id="+quizId);
        rd.forward(request, response);
    }

}
