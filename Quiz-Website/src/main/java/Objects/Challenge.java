package Objects;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * The Challenge class represents a challenge object in a quiz application.
 * It encapsulates the details of a challenge between two participants, including the sender,
 * receiver, associated quiz, and the timestamp when the challenge was created.
 */
public class Challenge {

    /**
     * Constant representing an unset or invalid challenge ID.
     * It is used when a challenge is created without a specified ID.
     */
    public static final long NO_ID = -1;

    /**
     * Constant representing an unset or unknown timestamp for the challenge.
     * It is used when a challenge is created without a specified timestamp.
     */
    public static final Timestamp NO_TIMESTAMP = null;

    /**
     * Unique identifier for the challenge.
     */
    private long id;

    /**
     * The ID of the sender who initiated the challenge.
     */
    private final long senderId;

    /**
     * The ID of the receiver who received the challenge.
     */
    private final long receiverId;

    /**
     * The ID of the quiz associated with the challenge.
     */
    private final long quizId;

    /**
     * Timestamp indicating when the challenge was created.
     */
    private Timestamp timestamp;

    /**
     * Parameterized constructor to create a Challenge object with all attributes specified.
     *
     * @param id         The unique identifier of the challenge.
     * @param senderId   The ID of the sender who initiated the challenge.
     * @param receiverId The ID of the receiver who received the challenge.
     * @param quizId     The ID of the quiz associated with the challenge.
     * @param timestamp  Timestamp indicating when the challenge was created.
     */
    public Challenge(long id, long senderId, long receiverId, long quizId, Timestamp timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.quizId = quizId;
        this.timestamp = timestamp;
    }

    /**
     * Parameterized constructor to create a Challenge object without an ID and a timestamp.
     * It uses the first constructor with NO_ID and NO_TIMESTAMP as default values.
     *
     * @param senderId   The ID of the sender who initiated the challenge.
     * @param receiverId The ID of the receiver who received the challenge.
     * @param quizId     The ID of the quiz associated with the challenge.
     */
    public Challenge(long senderId, long receiverId, long quizId) {
        this(NO_ID, senderId, receiverId, quizId, NO_TIMESTAMP);
    }

    /**
     * Setter for the challenge ID.
     *
     * @param id The new value for the challenge ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter for the challenge timestamp.
     *
     * @param timestamp The new timestamp value for the challenge.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter for the challenge ID.
     *
     * @return The challenge ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for the ID of the associated quiz.
     *
     * @return The ID of the associated quiz.
     */
    public long getQuizId() {
        return quizId;
    }

    /**
     * Getter for the ID of the receiver of the challenge.
     *
     * @return The ID of the receiver.
     */
    public long getReceiverId() {
        return receiverId;
    }

    /**
     * Getter for the ID of the sender of the challenge.
     *
     * @return The ID of the sender.
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * Getter for the challenge timestamp.
     *
     * @return The challenge timestamp.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }
}

