package Objects.Questions;

/**
 * Represents a Picture-Response type of quiz question.
 */
public class PictureResponse implements Question {

    // The URL of the image to be displayed for the question.
    private final String imageURL;

    // The text of the question.
    private final String questionText;

    // The correct response to the picture-response question.
    private final String correctAnswer;

    /**
     * Creates a new Picture-Response object with the specified image URL and correct answer.
     *
     * @param imageURL      The URL of the image to be displayed for the picture-response question.
     * @param questionText  The text of the picture-response question.
     * @param correctAnswer The correct response to the picture-response question.
     */
    public PictureResponse(String imageURL, String questionText, String correctAnswer) {
        this.imageURL = imageURL;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the image url of the picture-response question.
     *
     * @return The image url of the picture-response question.
     */
    public String getImageURL(){
        return imageURL;
    }

    /**
     * Gets the text of the picture-response question.
     *
     * @return The text of the picture-response question.
     */
    @Override
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Checks if the user's answer matches the correct answer for the picture-response question.
     *
     * @param userAnswer The user's response to the picture-response question.
     * @return true if the user's answer is correct, false otherwise.
     */
    @Override
    public boolean checkAnswer(String userAnswer) {
        return correctAnswer.equalsIgnoreCase(userAnswer.trim());
    }
}

