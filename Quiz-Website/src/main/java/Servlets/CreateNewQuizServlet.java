package Servlets;

import DAOinterfaces.QuizDao;
import DAOinterfaces.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CreateNewQuizServlet extends HttpServlet {

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // Implementation is not necessary
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
        UserDao userSQL = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

        String quizName = request.getParameter("quizName");
        String description = request.getParameter("description");
        boolean isPractice = Boolean.parseBoolean(request.getParameter("isPractice"));

        String mainUserID = (String) request.getSession().getAttribute("MainUserID");

        if(mainUserID == null) return;


        long id = quizSQL.addNewQuiz(userSQL.getUserById(Long.parseLong(mainUserID)), quizName, description, true, isPractice);

        if(id != -1){
            RequestDispatcher rd = request.getRequestDispatcher("editQuizQuestions.jsp?quiz_id=" +id);
            rd.forward(request, response);
        }
    }
}
