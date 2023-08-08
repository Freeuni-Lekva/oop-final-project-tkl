package Servlets;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizOptionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation in the POST method for this servlet
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Getting Quiz And Questions SQL Classes
        QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
        QuestionsDao questionsSQL = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);

        // Getting all button elements to determine on which button user pressed
        String isDeleteQuestion = request.getParameter("deleteQuestion");
        String isActivateQuiz = request.getParameter("activateQuiz");
        String isDeactivateQuiz = request.getParameter("deactivateQuiz");
        String isActivatePractice = request.getParameter("activatePractice");
        String isDeactivatePractice = request.getParameter("deactivatePractice");
        String isDeleteQuiz = request.getParameter("deleteQuiz");

        String quizId = request.getParameter("quiz_id");

        if(quizId == null){
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        if(isDeleteQuestion != null){

            String questionId = request.getParameter("question_id");
            if(questionId != null) questionsSQL.deleteQuestion(Long.parseLong(questionId));

        }else if(isActivateQuiz != null){

            quizSQL.changeDraftStatus(Long.parseLong(quizId), false);

        }else if(isDeactivateQuiz != null){

            quizSQL.changeDraftStatus(Long.parseLong(quizId), true);

        }else if(isActivatePractice != null){

            quizSQL.changePracticeStatus(Long.parseLong(quizId), true);

        }else if(isDeactivatePractice != null){

            quizSQL.changePracticeStatus(Long.parseLong(quizId), false);

        }else if(isDeleteQuiz != null){

            quizSQL.removeQuizById(Long.parseLong(quizId));
            RequestDispatcher rd = request.getRequestDispatcher("createQuiz.jsp");
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("editQuizQuestions.jsp?quiz_id=" +quizId);
        rd.forward(request, response);
    }
}
