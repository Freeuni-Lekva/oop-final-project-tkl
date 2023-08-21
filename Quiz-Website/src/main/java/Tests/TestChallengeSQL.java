package Tests;

import DAOinterfaces.ChallengeDao;
import DAOinterfaces.QuizDao;
import DAOinterfaces.UserDao;
import DAOs.ChallengeSQL;
import DAOs.FriendRequestSQL;
import DAOs.QuizSQL;
import DAOs.UserSQL;
import Objects.Challenge;
import Objects.Quiz;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TestChallengeSQL extends TestCase {
    private User user1;
    private User user2;
    private UserDao userDao;
    private QuizDao quizDao;
    private ChallengeDao challengeDao;
    private Quiz quiz;

    @Before
    public void setUp(){
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("challenge");
        ServerConfigurations.clearTable("quizzes");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();

        userDao = new UserSQL(dataSource);
        quizDao = new QuizSQL(dataSource);
        challengeDao = new ChallengeSQL(dataSource);

        userDao.register("user1", "user1", "user1", "user1");
        userDao.register("user2", "user2", "user2", "user2");

        user1 = userDao.getUserByName("user1");
        user2 = userDao.getUserByName("user2");

        quizDao.addNewQuiz(user1, "testQuiz", "testQuiz", false, false, false);
        quiz = quizDao.getQuizzesByCreatorId(user1.getId()).get(0);
    }

    public void testSendChallenge(){
        boolean isSuccess = challengeDao.sendChallenge(new Challenge(user1.getId(), user2.getId(), quiz.getQuizId()));
        assertTrue(isSuccess);
    }

    public void testGetChallengesForUser(){
        Challenge challenge = SendAndRetrieveChallenge();
        assertEquals(quiz.getQuizId(), challenge.getQuizId());
        assertEquals(user1.getId(), challenge.getSenderId());
        assertEquals(user2.getId(), challenge.getReceiverId());
    }

    public void testGetChallenge(){
        Challenge challenge = SendAndRetrieveChallenge();
        Challenge challenge1 = challengeDao.getChallenge(challenge.getId());

        assertEquals(challenge.getId(), challenge1.getId());
        assertEquals(challenge.getQuizId(), challenge1.getQuizId());
        assertEquals(challenge.getReceiverId(), challenge1.getReceiverId());
        assertEquals(challenge.getSenderId(), challenge1.getSenderId());
        assertEquals(challenge.getTimestamp(), challenge1.getTimestamp());
    }

    public void testRemoveChallenge(){
        Challenge challenge = SendAndRetrieveChallenge();
        long id = challenge.getId();

        boolean isSuccess1 = challengeDao.removeChallenge(id);

        assertTrue(isSuccess1);
        assertNull(challengeDao.getChallenge(id));
        assertEquals(0, challengeDao.getChallengesForUser(user2.getId()).size());
    }

    //this method also checks that challenge is successfully added and retrieved list is size of 1
    private Challenge SendAndRetrieveChallenge() {
        boolean isSuccess = challengeDao.sendChallenge(new Challenge(user1.getId(), user2.getId(), quiz.getQuizId()));
        assertTrue(isSuccess);

        List<Challenge> challengeList = challengeDao.getChallengesForUser(user2.getId());
        assertEquals(1, challengeList.size());

        return challengeList.get(0);
    }

}
