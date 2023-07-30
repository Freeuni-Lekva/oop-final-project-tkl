package Objects.Questions;

import java.util.List;

public abstract class Question {


    // The text of the question with a blank to be filled by the user.
    protected final String questionText;

    // The correct answers for question
    protected final List<String> correctAnswers;

    public Question(String questionText, List<String> correctAnswers){
        this.questionText = questionText;
        this.correctAnswers = correctAnswers;
    }

    /** if "correctAnswers" contains received argument function returns true
     * false otherwise
     */
    boolean checkAnswer(String userAnswer){

        for(String correctAnswer: correctAnswers){
            if(correctAnswer.equals(userAnswer)) return true;
        }

        return false;
    }

    /** simple getter function
     */
    public String getQuestionText() {

        return questionText;
    }
}
