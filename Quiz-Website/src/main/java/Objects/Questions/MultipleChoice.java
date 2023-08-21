package Objects.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Multiple Choice type of quiz question.
 */
public class MultipleChoice extends QuestionResponse {

    // The array of choices available for the user to select from.
    private final List<String> possibleAnswers;

    // The index of the correct choice in the choices array.
    private final List<Integer> correctChoiceIndex;

    /**
     * Creates a new Multiple Choice object with the specified question, choices, and correct choice index.
     *
     * @param questionText The text of the multiple-choice question.
     * @param possibleAnswers The list of choices available for the user to select from.
     * @param correctChoiceIndex The index of the correct choice in the choices array.
     */
    public MultipleChoice(long id, String questionText, List<String> possibleAnswers, List<Integer> correctChoiceIndex) {
        super(id, questionText, null);
        this.possibleAnswers = possibleAnswers;
        this.correctChoiceIndex = correctChoiceIndex;
    }

    /** Gets the available choices of the multiple-choice question.
     *
     * @return The array of strings of the available choices.
     */
    public List<String> getPossibleAnswers(){
        return possibleAnswers;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        for(int correctIndex: correctChoiceIndex){
            if(possibleAnswers.get(correctIndex).equalsIgnoreCase(userAnswer)) return true;
        }
        return false;
    }

    @Override
    public List<String> getCorrectAnswers(){
        List<String> result = new ArrayList<>();
        for (int index :
                correctChoiceIndex) {
            result.add(possibleAnswers.get(index));
        }

        return result;
    }

    public String getHTMLCode(){

        String result = "";

        for(int i = 0; i < possibleAnswers.size(); i++){
            result += "<input type=\"radio\" name=\"" + id + "\" value=\"" + possibleAnswers.get(i) + "\" required>" + possibleAnswers.get(i) +"<br>\n";
        }
        return result;
    }
}

