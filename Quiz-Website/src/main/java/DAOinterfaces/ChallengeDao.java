package DAOinterfaces;

import Objects.Challenge;
import java.util.List;

/**
 * The ChallengeDao interface provides the contract for accessing and managing challenges in a quiz application.
 * It defines methods for sending challenges, retrieving challenges by ID, and obtaining a list of challenges
 * associated with a specific user.
 */
public interface ChallengeDao {

    /**
     * Constant representing the attribute name to be used for accessing the ChallengeDao implementation
     * in a context, such as a servlet context or session.
     */
    String ATTRIBUTE_NAME = "challengeDao";

    /**
     * Constant representing the attribute name to be used for accessing the ChallengeDao message
     * in a context, such as a servlet context or session.
     */
    String MESSAGE_ATTRIBUTE_NAME = "challengeMessage";

    /**
     * Sends a challenge to the recipient.
     *
     * @param challenge The Challenge object representing the challenge to be sent.
     * @return true if the challenge is successfully sent, false otherwise.
     */
    boolean sendChallenge(Challenge challenge);


    /**
     * Retrieves a challenge by its ID.
     *
     * @param id The unique identifier of the challenge to retrieve.
     * @return A Challenge object with the specified id, null if not found.
     */
    Challenge getChallenge(long id);


    /**
     * Retrieves a list of challenges associated with a specific user.
     *
     * @param userId The ID of the user for whom to fetch the challenges.
     * @return A List containing Challenge objects associated with the specified user.
     */
    List<Challenge> getChallengesForUser(long userId);

    /**
     * Removes a challenge from the database based on its ID.
     *
     * @param id The unique identifier of the challenge to be removed.
     * @return true if the challenge is successfully removed, false otherwise.
     */
    boolean removeChallenge(long id);
}