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
        String isActivateQuestionSorted = request.getParameter("activateSortedQuestions");
        String isDeactivateQuestionSorted = request.getParameter("deactivateSortedQuestions");
        String isActivateMultiplePage = request.getParameter("activateMultiplePage");
        String isActivateOnePage = request.getParameter("activateOnePage");

        String quizIdString = request.getParameter("quiz_id");

        if(quizIdString == null){
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        long quizId = Long.parseLong(quizIdString);

        if(isDeleteQuestion != null){

            String questionId = request.getParameter("question_id");
            if(questionId != null) questionsSQL.deleteQuestion(Long.parseLong(questionId));

        }else if(isActivateQuiz != null){

            quizSQL.changeDraftStatus(quizId, false);

        }else if(isDeactivateQuiz != null){

            quizSQL.changeDraftStatus(quizId, true);

        }else if(isActivatePractice != null){

            quizSQL.changePracticeStatus(quizId, true);

        }else if(isDeactivatePractice != null){

            quizSQL.changePracticeStatus(quizId, false);

        }else if(isActivateQuestionSorted != null) {

            quizSQL.changeSortingStatus(quizId, true);

        }else if(isDeactivateQuestionSorted != null){

            quizSQL.changeSortingStatus(quizId, false);

        }else if(isActivateMultiplePage != null){

            quizSQL.changeQuestionShowingOption(quizId, false);

        }else if(isActivateOnePage != null){

            quizSQL.changeQuestionShowingOption(quizId, true);

        }else if(isDeleteQuiz != null){

            quizSQL.removeQuizById(quizId);
            RequestDispatcher rd = request.getRequestDispatcher("createQuiz.jsp");
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("editQuizQuestions.jsp?quiz_id=" +quizId);
        rd.forward(request, response);
    }
}
