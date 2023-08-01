package DAOinterfaces;

import Objects.Quiz;
import Objects.User;

import java.util.Date;
import java.util.List;

public interface QuizDao {

    int SERVER_ERROR = -4;
    int ACCOUNT_NOT_FOUND = -3;
    int NOT_ENOUGH_INFORMATION = -2;
    int QUIZ_NOT_ADDED = -1;

    String ATTRIBUTE_NAME = "quizDao";

    /** function receives sql "Where" condition and returns list of quizzes according to that
     * condition, function returns Empty list default
     */
    List<Quiz> getQuizzes(String condition);

    /** functions returns Quiz object which id equals to received parameter, if there is not
     * Quiz with received id in DB function returns null
     */
    Quiz getQuizById(long id);

    /** function returns list of quizzes which is created by user,which id equals to received argument
     * function returns empty list default
     */
    List<Quiz> getQuizzesByCreatorId(long id);

    /** function tries to remove quiz from DB which id equals to received argument
     * function returns true if Quiz is removed from DB successfully
     */
    boolean removeQuizById(long id);

    /** function tries to add new quiz to DB
     * function returns -1 if error occurs, id if new quiz otherwise
     */
    long addNewQuiz(User creator, String quizName, String description, boolean isDraft, boolean isPractice);

    /** function tries to change is_draft option for quiz
     * function returns true if change applies to DB, false otherwise
     */
    boolean changeDraftStatus(long id, boolean isDraft);

    /** function tries to change is_practice option for quiz
     * function return true if change applies to DB, false otherwise
     */
    boolean changePracticeStatus(long id, boolean isPractice);
}
