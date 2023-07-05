package DAOinterfaces;

import Objects.User;

import java.util.List;

public interface FriendsDao {
    // Constants for success and failure codes
    int SUCCESS_ADDED = 1;
    int FAILED_ADDED = 2;
    int SUCCESS_REMOVED = 3;
    int FAILED_REMOVED = 4;

    // Attribute name for the DAO implementation
    String ATTRIBUTE_NAME = "friendshipDao";

    /** Retrieves a list of friends for a given user
     */
    List<User> getUserFriends(Long user_id);

    /** Adds a friendship between two users
     */

    int addFriendship(Long user_id1, Long user_id2);

    /** function removes friendship between received users
     */
    int removeFriendship(Long user_id1, Long user_id2);

    /** function returns true if two users are friend
     */
    boolean checkIfFriends(Long user_id1, Long user_id2);
}
