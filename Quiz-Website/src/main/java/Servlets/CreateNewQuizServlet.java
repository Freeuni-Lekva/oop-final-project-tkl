package Servlets;

import DAOinterfaces.QuizDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class CreateNewQuizServlet extends HttpServlet {

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // Implementation is not necessary
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);

        String quizName = request.getParameter("quizName");
        String description = request.getParameter("description");
        boolean isPractice = Boolean.parseBoolean(request.getParameter("isPractice"));

        String mainUserID = (String) request.getSession().getAttribute("MainUserID");

        if(mainUserID == null){
            System.out.println("Null Aris User");
            return;
        }

        int id = quizSQL.addNewQuiz(Long.parseLong(mainUserID), quizName, description, new Date(), true, isPractice);

        if(id != -1){
            System.out.println("quiz added successfully");
            RequestDispatcher rd = request.getRequestDispatcher("editQuizQuestions.jsp");
            rd.forward(request, response);
            return;
        }

        System.out.println("adding to db was unsuccessful");
    }
}
