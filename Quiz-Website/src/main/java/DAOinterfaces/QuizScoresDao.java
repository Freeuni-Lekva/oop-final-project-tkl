package DAOinterfaces;

import Objects.Score;

import javax.xml.registry.infomodel.User;
import java.util.List;
import java.util.Map;

public interface QuizScoresDao {

    /**
     * The attribute name used to store an instance of QuizScoresDao in the ServletContext.
     */
    String ATTRIBUTE_NAME = "quizScoresDao";

    /**
     * Adds a new quiz score entry to the data storage.
     *
     * @param userId    The ID of the user who took the quiz.
     * @param quizId    The ID of the quiz for which the score is recorded.
     * @param score     The score achieved by the user.
     * @param startTime The time taken user started the quiz.
     * @return id of the newly inserted row
     */
    long addNewScore(long userId, long quizId, double score, long startTime);

    /**
     * Retrieves the maximum score achieved for a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return The maximum score achieved for the quiz.
     */
    double getMaxScore(long quizId);

    /**
     * Retrieves a list of user IDs who have taken a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return A list of user IDs who have taken the quiz.
     */
    List<Long> getUserIds(long quizId);


    /**
     * Retrieves a list of score objects where it stores the highest score and its corresponding time for given quiz
     * @param quizId the ID of the quizz.
     * @return A list of Score objects.
     */
    public List<Score> getBestScoresAndTimesForQuiz(long quizId);

    /**
     * Retrieves a list of quiz IDs that user has taken.
     *
     * @param userId The ID of the user.
     * @return A list of quiz IDs that user has taken.
     */
    List<Long> getQuizIds(long userId);

    /**
     * Retrieves a map of quiz IDs and user's scores for that quiz.
     *
     * @param userId
     * @return A map of quiz IDs and list of its corresponding scores.
     */
    Map<Long, List<Double>> getScoresForUser(long userId);

    /**
     * Retrieves a map of quiz IDs and time the user has taken to complete it
     *
     * @param userId The ID of the user.
     * @return A map of quiz IDs and list of user's times taken for the quiz.
     */
    Map<Long, List<Long>> getTimeTakenForUser(long userId);

    /**
     * Retrieves one single score for given id
     * @param id
     * @return Score on given id
     */
    public Score getScore(long id);
}
