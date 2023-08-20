package Tests;

import DAOinterfaces.QuestionsDao;
import DAOinterfaces.QuizDao;
import DAOinterfaces.UserDao;
import DAOs.QuestionsSQL;
import DAOs.QuizSQL;
import DAOs.UserSQL;
import Objects.Questions.Question;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Arrays;
import java.util.List;

public class TestQuestionsSQL extends TestCase {

    public void testAllFunctions(){
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("quizzes");
        ServerConfigurations.clearTable("questions");
        ServerConfigurations.clearTable("simple_answers");
        ServerConfigurations.clearTable("multiple_choice_answers");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();
        UserDao userSQL = new UserSQL(dataSource);
        QuizDao quizSQL = new QuizSQL(dataSource);
        QuestionsDao questionsSQL = new QuestionsSQL(dataSource);

        // Create new user and quiz
        userSQL.register("toms343", "toma", "dadiani", "t");
        User user = userSQL.getUserByName("toms343");
        long id = quizSQL.addNewQuiz(user, "Test Quiz", "Test Quiz Description", true, false);

        // Add questions to quiz
        questionsSQL.addSimpleQuestions(quizSQL.getQuizById(id), "Joseph Stalin Or Adolf Hitler?", Arrays.asList("Joseph", "Stalin", "Soso"), null);
        questionsSQL.addSimpleQuestions(quizSQL.getQuizById(id), "What is on picture?", Arrays.asList("Batman", "Betmeni", "Gamura"), "default_picture.jpg");
        questionsSQL.addMultipleChoiceQuestions(quizSQL.getQuizById(id), "Multiple choice", Arrays.asList("A", "B", "C", "D", "E", "F"), Arrays.asList(0, 2, 4));

        List<Question> questions = questionsSQL.getQuizQuestions(id, quizSQL.getQuizById(id).isQuestionsSorted());
        assertEquals(questions.size(), 3);

        assertEquals(questions.get(0).getQuestion(), "Joseph Stalin Or Adolf Hitler?");
        assertEquals(questions.get(1).getQuestion(), "What is on picture?");
        assertEquals(questions.get(2).getQuestion(), "Multiple choice");
        assertEquals(questions.get(1).getImageURL(), "default_picture.jpg");

        assertTrue(questions.get(0).checkAnswer("Soso"));
        assertTrue(questions.get(0).checkAnswer("soso"));
        assertTrue(questions.get(0).checkAnswer("Joseph"));
        assertFalse(questions.get(0).checkAnswer("hitler"));
        assertFalse(questions.get(1).checkAnswer("superman"));
        assertFalse(questions.get(2).checkAnswer("M"));

        assertTrue(questionsSQL.deleteQuestion(questions.get(1).getId()));
        assertFalse(questionsSQL.deleteQuestion(questions.get(questions.size() - 1).getId() + 10));
        assertNull(questionsSQL.getQuestionById(-1));
        questions = questionsSQL.getQuizQuestions(id, quizSQL.getQuizById(id).isQuestionsSorted());
        assertEquals(questions.size(), 2);
    }
}
