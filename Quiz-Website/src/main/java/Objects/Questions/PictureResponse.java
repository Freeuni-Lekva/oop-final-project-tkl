package Objects.Questions;

import java.util.List;

/**
 * Represents a Picture-Response type of quiz question.
 */
public class PictureResponse extends QuestionResponse {

    // The URL of the image to be displayed for the question.
    private final String imageURL;

    /**
     * Creates a new Picture-Response object with the specified image URL and correct answer.
     *
     * @param imageURL      The URL of the image to be displayed for the picture-response question.
     * @param questionText  The text of the picture-response question.
     * @param correctAnswers The correct response to the picture-response question.
     */
    public PictureResponse(long id, String imageURL, String questionText, List<String> correctAnswers) {
        super(id, questionText, correctAnswers);
        this.imageURL = imageURL;
    }

    /**
     * Gets the image url of the picture-response question.
     *
     * @return The image url of the picture-response question.
     */
    public String getImageURL(){
        return imageURL;
    }
}

