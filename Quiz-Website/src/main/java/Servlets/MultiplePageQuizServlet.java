package Servlets;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizDao;
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

        if(questions.size() == index + 1){

            RequestDispatcher rd = request.getRequestDispatcher("multiplePageQuizResult.jsp");
            rd.forward(request, response);
            return;
        }

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
        request.getSession().setAttribute("startTime", new Date().getTime());

        // Redirect user to next page
        RequestDispatcher rd = request.getRequestDispatcher("startMultiplePageQuiz.jsp?quiz_id="+quizId);
        rd.forward(request, response);

    }
}
