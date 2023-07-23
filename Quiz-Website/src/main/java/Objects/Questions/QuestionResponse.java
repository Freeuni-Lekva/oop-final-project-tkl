package Objects.Questions;

/**
 * Represents a Question-Response type of quiz question.
 */
public class QuestionResponse implements Question {

    // The text of the question.
    private final String questionText;

    // The correct response to the question.
    private final String correctAnswer;

    /**
     * Creates a new Question-Response object with the specified question and correct answer.
     *
     * @param questionText The text of the question.
     * @param correctAnswer The correct response to the question.
     */
    public QuestionResponse(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the text of the question.
     *
     * @return The text of the question.
     */
    @Override
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Checks if the user's answer matches the correct answer for this question.
     *
     * @param userAnswer The user's response to the question.
     * @return true if the user's answer is correct, false otherwise.
     */
    @Override
    public boolean checkAnswer(String userAnswer) {
        return correctAnswer.equalsIgnoreCase(userAnswer.trim());
    }
}

