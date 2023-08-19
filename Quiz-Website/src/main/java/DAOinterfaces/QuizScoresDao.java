package DAOinterfaces;

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
     */
    void addNewScore(long userId, long quizId, double score, long startTime);

    /**
     * Retrieves the maximum score achieved for a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return The maximum score achieved for the quiz.
     */
    double getMaxScore(long quizId);

    /**
     * Retrieves the score achieved by a specific user for a specific quiz.
     *
     * @param userId The ID of the user.
     * @param quizId The ID of the quiz.
     * @return The score achieved by the user for the quiz.
     */
    double getScore(long userId, long quizId);

    /**
     * Retrieves the time taken by a specific user to complete a specific quiz.
     *
     * @param userId The ID of the user.
     * @param quizId The ID of the quiz.
     * @return The time taken by the user to complete the quiz.
     */
    long getTimeTaken(long userId, long quizId);

    /**
     * Retrieves a list of user IDs who have taken a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return A list of user IDs who have taken the quiz.
     */
    List<Long> getUserIds(long quizId);

    /**
     * Retrieves a list of quiz IDs that a specific user has taken.
     *
     * @param userId The ID of the user.
     * @return A list of quiz IDs taken by the user.
     */
    List<Long> getQuizIds(long userId);

    /**
     * Retrieves a map of user IDs and their corresponding scores for a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return A map of user IDs and their scores for the quiz.
     */
    Map<Long, Double> getScoresForQuiz(long quizId);

    /**
     * Retrieves a map of user IDs and their corresponding time taken for a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return A map of user IDs and their time taken for the quiz.
     */
    Map<Long, Long> getTimeTakenForQuiz(long quizId);
}
