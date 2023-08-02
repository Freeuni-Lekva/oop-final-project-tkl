package Objects.Questions;

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
}
