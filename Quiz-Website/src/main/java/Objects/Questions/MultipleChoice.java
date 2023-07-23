package Objects.Questions;

/**
 * Represents a Multiple Choice type of quiz question.
 */
public class MultipleChoice implements Question {

    // The text of the question.
    private final String questionText;

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
    public MultipleChoice(String questionText, String[] choices, int correctChoiceIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
    }

    /**
     * Gets the text of the multiple-choice question along with the available choices.
     *
     * @return The text of the multiple-choice question with the available choices.
     */
    @Override
    public String getQuestionText() {
        StringBuilder questionWithChoices = new StringBuilder(questionText);
        for (int i = 0; i < choices.length; i++) {
            questionWithChoices.append("\n").append((char) ('A' + i)).append(". ").append(choices[i]);
        }
        return questionWithChoices.toString();
    }

    /**
     * Gets the available choices of the multiple-choice question.
     *
     * @return The array of strings of the available choices.
     */
    public String[] getChoices(){
        return choices;
    }

    /**
     * Checks if the user's answer matches the correct choice for the multiple-choice question.
     *
     * @param userAnswer The user's response to the multiple-choice question (e.g., "A", "B", etc.).
     * @return true if the user's answer is correct, false otherwise.
     */
    @Override
    public boolean checkAnswer(String userAnswer) {
        char userChoice = Character.toUpperCase(userAnswer.trim().charAt(0));
        return userChoice - 'A' == correctChoiceIndex;
    }
}

