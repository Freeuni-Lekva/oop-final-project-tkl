package Objects.Questions;

/**
 * Represents a Fill in the Blank type of quiz question.
 */
public class FillInTheBlank implements Question {

    // The text of the question with a blank to be filled by the user.
    private final String questionText;

    // The correct answer to the fill-in-the-blank question.
    private final String correctAnswer;

    /**
     * Creates a new Fill in the Blank object with the specified question and correct answer.
     *
     * @param questionText The text of the question with a blank to be filled by the user.
     * @param correctAnswer The correct answer to the fill-in-the-blank question.
     */
    public FillInTheBlank(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the text of the fill-in-the-blank question.
     *
     * @return The text of the fill-in-the-blank question.
     */
    @Override
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Checks if the user's answer matches the correct answer for the fill-in-the-blank question.
     *
     * @param userAnswer The user's response to the fill-in-the-blank question.
     * @return true if the user's answer is correct, false otherwise.
     */
    @Override
    public boolean checkAnswer(String userAnswer) {
        return correctAnswer.equalsIgnoreCase(userAnswer.trim());
    }
}

