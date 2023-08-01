package Objects.Questions;

import java.util.List;

public class QuestionResponse implements Question{


    // The text of the question with a blank to be filled by the user.
    protected final String questionText;

    // The correct answers for question
    protected final List<String> correctAnswers;
    protected final long id;

    public QuestionResponse(long id, String questionText, List<String> correctAnswers){
        this.questionText = questionText;
        this.correctAnswers = correctAnswers;
        this.id = id;
    }

    /** if "correctAnswers" contains received argument function returns true
     * false otherwise
     */
    public boolean checkAnswer(String userAnswer){

        for(String correctAnswer: correctAnswers){
            if(correctAnswer.equals(userAnswer)) return true;
        }

        return false;
    }

    /** simple getter function
     */
    public String getQuestion(){
        return questionText;
    }

    @Override
    public long getId() {
        return id;
    }
}
