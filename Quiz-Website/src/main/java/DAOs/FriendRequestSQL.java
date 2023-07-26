package DAOs;

import DAOinterfaces.FriendRequestDao;
import Objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestSQL implements FriendRequestDao {
    private final BasicDataSource dataSource;
    public FriendRequestSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    /** Simple helper function which returns PreparedStatement built according to received arguments
     */
    private PreparedStatement prepareStatement(String query, long val1, long val2){

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement result = connection.prepareStatement(query);
            result.setLong(1, val1);
            result.setLong(2, val2);

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Adds a friend request from sender to receiver
    @Override
    public int addFriendRequest(Long sender_id, Long receiver_id) {
        if (checkFriendRequest(sender_id, receiver_id)) return FAILED_FRIEND_REQUEST;
        try {

            PreparedStatement preparedStatement = prepareStatement( "insert into friend_requests (sender_id, receiver_id) values (?, ?)", sender_id, receiver_id);
            if(preparedStatement.executeUpdate() != 0) return SUCCESS_FRIEND_REQUEST;

        } catch (SQLException e) {
            return FAILED_FRIEND_REQUEST;
        }
        return FAILED_FRIEND_REQUEST;
    }

    // Checks if a friend request exists from sender to receiver
    @Override
    public boolean checkFriendRequest(Long sender_id, Long receiver_id) {
        try {

            PreparedStatement preparedStatement = prepareStatement("select * from friend_requests where sender_id = ? and receiver_id = ?", sender_id, receiver_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            return false;
        }
    }

    // Removes a friend request from sender to receiver
    @Override
    public int removeFriendRequest(Long sender_id, Long receiver_id) {
        if(!checkFriendRequest(sender_id, receiver_id)) return FAILED_FRIEND_REQUEST_REMOVE;
        try {
            PreparedStatement preparedStatement = prepareStatement("delete from friend_requests where sender_id = ? and receiver_id = ?", sender_id, receiver_id);
            if(preparedStatement.executeUpdate() != 0) return SUCCESS_FRIEND_REQUEST_REMOVE;

        } catch (SQLException e) {
            return FAILED_FRIEND_REQUEST_REMOVE;
        }
        return FAILED_FRIEND_REQUEST_REMOVE;
    }

    // Retrieves a list of received friend requests for a given user
    @Override
    public List<User> getReceivedFriendRequests(Long user_id) {

        try(Connection connection = dataSource.getConnection()) {

            // Prepare the first SQL query to retrieve sender names
            String sqlQuery = "SELECT sender_id FROM friend_requests WHERE receiver_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, user_id);

            // Execute the first query to retrieve sender ids
            ResultSet resultSet = preparedStatement.executeQuery();

            // Prepare the second SQL query to retrieve user details
            String second_query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement secondStatement = connection.prepareStatement(second_query);

            // Create a list to store the resulting users
            List<User> result = new ArrayList<>();

            // Process each received friend request
            while (resultSet.next()){
                secondStatement.setLong(1, resultSet.getLong(1));

                // Execute the second query to retrieve user details
                ResultSet resultSet1 = secondStatement.executeQuery();
                resultSet1.next();

                // Create a User object and add it to the result list
                User user = new User(resultSet1.getLong(1), resultSet1.getString(2), resultSet1.getString(3),
                        resultSet1.getString(4), resultSet1.getString(5), resultSet1.getString(6), resultSet1.getString(7));
                result.add(user);
            }

            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
