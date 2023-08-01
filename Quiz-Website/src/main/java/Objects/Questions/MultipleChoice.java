package Objects.Questions;

/**
 * Represents a Multiple Choice type of quiz question.
 */
public class MultipleChoice extends QuestionResponse {

    // The array of choices available for the user to select from.
    private final String[] choices;

    // The index of the correct choice in the choices array.
    private final int correctChoiceIndex;

    /**
     * Creates a new Multiple Choice object with the specified question, choices, and correct choice index.
     *
     * @param questionText The text of the multiple-choice question.
     * @param choices The array of choices available for the user to select from.
     * @param correctChoiceIndex The index of the correct choice in the choices array.
     */
    public MultipleChoice(long id, String questionText, String[] choices, int correctChoiceIndex) {
        super(id, questionText, null);
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
    }

    /** Gets the available choices of the multiple-choice question.
     *
     * @return The array of strings of the available choices.
     */
    public String[] getChoices(){
        return choices;
    }
}

