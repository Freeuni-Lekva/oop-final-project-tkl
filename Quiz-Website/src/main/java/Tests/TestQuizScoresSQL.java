package Tests;

import DAOinterfaces.ChallengeDao;
import DAOinterfaces.QuizDao;
import DAOinterfaces.QuizScoresDao;
import DAOinterfaces.UserDao;
import DAOs.ChallengeSQL;
import DAOs.QuizSQL;
import DAOs.QuizScoresSQL;
import DAOs.UserSQL;
import Objects.Quiz;
import Objects.Score;
import Objects.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestQuizScoresSQL {

    private BasicDataSource dataSource;
    private User user;
    private Quiz quiz;
    private QuizScoresDao quizScoresDao;

    @Before
    public void setUp() {
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("quizzes");
        ServerConfigurations.clearTable("quiz_scores");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();

        UserDao userDao = new UserSQL(dataSource);
        QuizDao quizDao = new QuizSQL(dataSource);
        quizScoresDao = new QuizScoresSQL(dataSource);

        userDao.register("user1", "user1", "user1", "user1");

        user = userDao.getUserByName("user1");

        quizDao.addNewQuiz(user, "testQuiz", "testQuiz", false, false, false);
        quiz = quizDao.getQuizzesByCreatorId(user.getId()).get(0);
    }

    @Test
    public void testAddNewScore() {
        long id = quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());
        assertNotEquals(-1, id);
    }


    @Test
    public void testGetUserIds() {
        quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());

        List<Long> userIds = quizScoresDao.getUserIds(quiz.getQuizId());
        assertEquals(1, userIds.size());
        assertEquals(Optional.of(user.getId()), Optional.of(userIds.get(0)));
    }

    @Test
    public void testGetBestScoresAndTimesForQuiz() {
        quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());

        List<Score> scores = quizScoresDao.getBestScoresAndTimesForQuiz(quiz.getQuizId());
        assertEquals(1, scores.size());

        Score score = scores.get(0);
        assertEquals(user.getId(), score.getUserId());
        assertEquals(quiz.getQuizId(), score.getQuizId());
        assertEquals(100, score.getScore(), .0);
    }

    @Test
    public void testGetQuizIds() {
        quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());

        List<Long> quizIds = quizScoresDao.getQuizIds(user.getId());
        assertEquals(1, quizIds.size());
        assertEquals(Optional.of(quiz.getQuizId()), Optional.of(quizIds.get(0)));
    }

    @Test
    public void testGetScoresForUser() {
        quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());

        Map<Long, List<Double>> scoresMap = quizScoresDao.getScoresForUser(user.getId());
        assertEquals(1, scoresMap.size());
        assertEquals(1, scoresMap.get(quiz.getQuizId()).size());
        assertEquals(100, scoresMap.get(quiz.getQuizId()).get(0), .0);
    }

    @Test
    public void testGetTimeTakenForUser() {
        quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());

        Map<Long, List<Long>> timeTakenMap = quizScoresDao.getTimeTakenForUser(user.getId());
        assertEquals(1, timeTakenMap.size());
        assertEquals(1, timeTakenMap.get(quiz.getQuizId()).size());
    }

    @Test
    public void testGetScore() {
        long id = quizScoresDao.addNewScore(user.getId(), quiz.getQuizId(), 100, System.currentTimeMillis());

        Score score = quizScoresDao.getScore(id);
        assertEquals(user.getId(), score.getUserId());
        assertEquals(quiz.getQuizId(), score.getQuizId());
        assertEquals(100, score.getScore(), .0);
    }
}