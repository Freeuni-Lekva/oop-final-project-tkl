package Tests;

import DAOinterfaces.QuizDao;
import DAOinterfaces.UserDao;
import DAOs.QuizSQL;
import DAOs.UserSQL;
import Objects.Quiz;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class TestQuizSQL extends TestCase {

    public void testAllFunctions(){
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("quizzes");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();
        UserDao users = new UserSQL(dataSource);
        QuizDao quizzes = new QuizSQL(dataSource);

        addNewUsers(users);
        addingQuizzes(users, quizzes);
        addingIncorrectQuizzes(users, quizzes);
        changingQuizzes(users, quizzes);
        deleteQuizzes(users, quizzes);
    }

    public void deleteQuizzes(UserDao users, QuizDao quizzes){
        User t1 = users.getUserByName("t1");
        List<Quiz> quizzes1 = quizzes.getQuizzesByCreatorId(t1.getId());

        for(Quiz quiz: quizzes1){
            quizzes.removeQuizById(quiz.getQuizId());
        }

        quizzes1 = quizzes.getQuizzesByCreatorId(t1.getId());
        assertEquals(0, quizzes1.size());

    }

    public void changingQuizzes(UserDao users, QuizDao quizzes){

        User t1 = users.getUserByName("t1");
        List<Quiz> quizzes1 = quizzes.getQuizzesByCreatorId(t1.getId());
        long quizId = quizzes1.get(0).getQuizId();
        assertEquals(1, quizzes1.size());

        quizzes.changeDraftStatus(quizId, false);
        quizzes.changePracticeStatus(quizId, false);

        assert(!quizzes.getQuizById(quizId).isDraft());
        assert(!quizzes.getQuizById(quizId).isPractice());

        quizzes.changeDraftStatus(quizId, true);
        quizzes.changePracticeStatus(quizId, true);

        assert(quizzes.getQuizById(quizId).isDraft());
        assert(quizzes.getQuizById(quizId).isPractice());
    }

    public void addingIncorrectQuizzes(UserDao users, QuizDao quizzes){

        User t1 = users.getUserByName("t1");

        long quizID3 = quizzes.addNewQuiz(null, "y", "a", false, false, true);
        long quizID4 = quizzes.addNewQuiz(t1, "", "asd", true, false, true);

        assertEquals(quizID3, QuizDao.ACCOUNT_NOT_FOUND);
        assertEquals(quizID4, QuizDao.NOT_ENOUGH_INFORMATION);
    }

    public void addingQuizzes(UserDao users, QuizDao quizzes){

        User t1 = users.getUserByName("t1");
        User t2 = users.getUserByName("t2");

        long quizID1 = quizzes.addNewQuiz(t1, "t1quiz", "t1description", true, false, true);
        long quizID2 = quizzes.addNewQuiz(t2, "t2quiz", "t2description", true, false, true);

        assert(quizID1 != QuizDao.QUIZ_NOT_ADDED);
        assert(quizID1 != QuizDao.NOT_ENOUGH_INFORMATION);
        assert(quizID1 != QuizDao.ACCOUNT_NOT_FOUND);
        assert(quizID2 != QuizDao.QUIZ_NOT_ADDED);
        assert(quizID2 != QuizDao.NOT_ENOUGH_INFORMATION);
        assert(quizID2 != QuizDao.ACCOUNT_NOT_FOUND);

        Quiz q1 = quizzes.getQuizById(quizID1);

        assertEquals(q1.getQuizId(), quizID1);
        assertEquals(q1.getCreatorId(), t1.getId());
        assertEquals(q1.getQuizName(), "t1quiz");
        assertEquals(q1.getDescription(), "t1description");
        assert(q1.isDraft());
        assert(!q1.isPractice());

        //just for coverage
        q1.getCreateTime();

    }

    public void addNewUsers(UserDao users){
        users.register("t1", "t", "t","t");
        users.register("t2", "t", "t","t");
    }
}
