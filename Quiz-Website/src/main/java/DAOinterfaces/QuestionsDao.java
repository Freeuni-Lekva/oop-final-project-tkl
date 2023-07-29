package DAOinterfaces;

import Objects.Questions.Question;

import java.util.List;

public interface QuestionsDao {

    String ATTRIBUTE_NAME = "questionDao";

    /** function returns all questions for quiz which id equals to received argument
     * function returns empty list if there is no quiz with that id
     */
    List<Question> getQuizQuestionsForQuiz(long id);

    /** function tries to add new question to DB, (arguments are not added yet)
     */
    boolean addQuestion();

    /** function tries to delete question from DB, returns false if something goes wrong
     */
    boolean deleteQuestion(long id);
}
