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
