package DAOinterfaces;

import Objects.Questions.Question;
import Objects.Quiz;

import java.util.List;

public interface QuestionsDao {

    int QUESTION_RESPONSE_TYPE = 1;
    int PICTURE_RESPONSE_TYPE = 2;
    int MULTIPLE_CHOICES_TYPE = 3;

    String ATTRIBUTE_NAME = "questionDao";

    /** function returns list of quizzes selected from db by condition,
     * function returns empty list if there is no questions which meets received conditions
     *
     * returned list of questions will be sorted by creation time
     */
    public List<Question> getQuestions(String condition);

    /** function returns question object, which id equals to received argument null  otherwise
     */
    Question getQuestionById(long id);

    /**
     * function returns all questions for quiz which id equals to received argument
     * function returns empty list if there is no quiz with that id
     *
     * returned list of questions will be sorted by questions id
     */
    List<Question> getQuizQuestions(long id, boolean isSorted);

    /** function tries to add new question to DB, function can't handle multiple choice questions
     * returns true if question adds to db successfully, false otherwise
     */
    boolean addSimpleQuestions(Quiz quiz, String question, List<String> answers, String imageURL);

    /** function tries to add new question to DB, function can only hande multiple choice type
     * returns true if question adds to DB successfully, false otherwise
     */
    boolean addMultipleChoiceQuestions(Quiz quiz, String question, List<String> possibleAnswers, List<Integer> correctAnswers);


    /** function tries to delete question from DB, returns false if something goes wrong
     */
    boolean deleteQuestion(long id);
}
