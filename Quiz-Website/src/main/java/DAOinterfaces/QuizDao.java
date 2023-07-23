package DAOinterfaces;

import Objects.Quiz;

import java.util.List;

public interface QuizDao {

    int QUESTION_RESPONSE_CATEGORY = 1;
    int FILL_BLANK_CATEGORY = 2;
    int MULTIPLE_CHOICES_CATEGORY = 3;
    int PICTURE_RESPONSE_CATEGORY = 4;

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
}
