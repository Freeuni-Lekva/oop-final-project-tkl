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
    List<User> getUserFriends(String user_name);

    /** Adds a friendship between two users
     */

    int addFriendship(String user_name1, String user_name2);

    /** function removes friendship between received users
     */
    int removeFriendship(String user_name1, String user_name2);

    /** function returns true if two users are friend
     */
    boolean checkIfFriends(String user_name1, String user_name2);
}
