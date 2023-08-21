package Objects.Questions;

import java.util.List;

public interface Question {

    /** function returns true if received argument is answer of question
     */
    boolean checkAnswer(String userAnswer);

    /** function returns text of question
     */
    String getQuestion();

    /** function returns id of question
     */
    long getId();

    /** function returns image url of question, if image is not associated to question it returns null
     */
    String getImageURL();

    /**
     * @return list of the correct answers for the given question
     */
    List<String> getCorrectAnswers();

    String getHTMLCode();
}
