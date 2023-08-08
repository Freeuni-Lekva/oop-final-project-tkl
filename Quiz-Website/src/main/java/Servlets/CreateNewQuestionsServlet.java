package Servlets;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizDao;
import Objects.Quiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateNewQuestionsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // Implementation is not necessary
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve data from request (type of question, question)
        String type = request.getParameter("questionType");
        String question = request.getParameter("question");
        List<String> answers = List.of(request.getParameterValues("answer"));

        // Getting questionsSQL and QuizSQL
        String quizId = request.getParameter("quiz_id");
        QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
        QuestionsDao questionsSQL = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);

        // If quiz id is null doPost method just returns user to main page
        if(quizId == null){
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        Quiz quiz = quizSQL.getQuizById(Long.parseLong(quizId));

        // If question type was multiple choice getting all correct indexes from request and add new Multiple choice question to DB
        if(type.equals("multiple-choice")){

            List<Integer> correctIndex = new ArrayList<>();
            List<String> correctIndexString = List.of(request.getParameterValues("correctAnswer"));

            // Iterating strings list and cast all element into the int
            for(String  index: correctIndexString){
                correctIndex.add(Integer.parseInt(index));
            }
            questionsSQL.addMultipleChoiceQuestions(quiz, question, answers, correctIndex);

        }else if(type.equals("picture-response")){

            // If user doesn't fill image input code adds default path for img
            String image = request.getParameter("imageURL");
            if(image.equals("")) image = "question_no_picture.jpg";
            questionsSQL.addSimpleQuestions(quiz, question, answers, image);

        }else if(type.equals("question-response")) questionsSQL.addSimpleQuestions(quiz, question, answers, null);

        RequestDispatcher rd = request.getRequestDispatcher("editQuizQuestions.jsp?quiz_id=" + Long.parseLong(quizId));
        rd.forward(request, response);
    }

}
