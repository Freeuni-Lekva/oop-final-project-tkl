package DAOinterfaces;

import Objects.User;

import java.util.List;

public interface FriendRequestDao {
    // Constants for success and failure codes
    int SUCCESS_FRIEND_REQUEST = 1;
    int FAILED_FRIEND_REQUEST = 2;
    int SUCCESS_FRIEND_REQUEST_REMOVE = 3;
    int FAILED_FRIEND_REQUEST_REMOVE = 4;

    // Attribute name for the DAO implementation
    String ATTRIBUTE_NAME = "friendRequestDao";

    /** Adds a friend request from the sender to the receiver
     */
    int addFriendRequest(Long sender_id, Long receiver_id);

    /** Checks if a friend request exists between the sender and the receiver
     */
    boolean checkFriendRequest(Long sender_id, Long receiver_id);

    /** Removes a friend request from the sender to the receiver
     */
    int removeFriendRequest(Long sender_id, Long receiver_id);

    /** Retrieves a list of received friend requests for a given user
     */
    List<User> getReceivedFriendRequests(Long user_id);

}
