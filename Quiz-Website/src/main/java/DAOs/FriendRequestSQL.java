package DAOs;

import DAOinterfaces.FriendRequestDao;
import Objects.User;
import jdk.vm.ci.meta.SpeculationLog;
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

    // Adds a friend request from sender to receiver
    @Override
    public int addFriendRequest(String sender_name, String receiver_name) {
        if (checkFriendRequest(sender_name, receiver_name)) return FAILED_FRIEND_REQUEST;
        try {
            // Establish a database connection
            Connection connection = dataSource.getConnection();

            // Prepare the SQL query
            String sqlQuery = "insert into friend_requests (sender_name, receiver_name) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, sender_name);
            preparedStatement.setString(2, receiver_name);

            // Execute the query and check if any rows were affected
            if(preparedStatement.executeUpdate() != 0) return SUCCESS_FRIEND_REQUEST;
        } catch (SQLException e) {
            return FAILED_FRIEND_REQUEST;
        }
        return FAILED_FRIEND_REQUEST;
    }

    // Checks if a friend request exists from sender to receiver
    @Override
    public boolean checkFriendRequest(String sender_name, String receiver_name) {
        try {
            // Establish a database connection
            Connection connection = dataSource.getConnection();

            // Prepare the SQL query
            String sqlQuery = "select * from friend_requests where sender_name = ? and receiver_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, sender_name);
            preparedStatement.setString(2, receiver_name);

            // Execute the query and check if a result is found
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    // Removes a friend request from sender to receiver
    @Override
    public int removeFriendRequest(String sender_name, String receiver_name) {
        if(!checkFriendRequest(sender_name, receiver_name)) return FAILED_FRIEND_REQUEST_REMOVE;
        try {
            // Establish a database connection
            Connection connection = dataSource.getConnection();

            // Prepare the SQL query
            String sqlQuery = "delete from friend_requests where sender_name = ? and receiver_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, sender_name);
            preparedStatement.setString(2, receiver_name);

            // Execute the query and check if any rows were affected
            if(preparedStatement.executeUpdate() != 0) return SUCCESS_FRIEND_REQUEST_REMOVE;
        } catch (SQLException e) {
            return FAILED_FRIEND_REQUEST_REMOVE;
        }
        return FAILED_FRIEND_REQUEST_REMOVE;
    }

    // Retrieves a list of received friend requests for a given user
    @Override
    public List<User> getReceivedFriendRequests(String user_name) {
        try {
            // Establish a database connection
            Connection connection = dataSource.getConnection();

            // Prepare the first SQL query to retrieve sender names
            String sqlQuery = "SELECT sender_name FROM friend_requests WHERE receiver_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user_name);

            // Execute the first query to retrieve sender names
            ResultSet resultSet = preparedStatement.executeQuery();

            // Prepare the second SQL query to retrieve user details
            String second_query = "SELECT * FROM users WHERE name = ?";
            PreparedStatement secondStatement = connection.prepareStatement(second_query);

            // Create a list to store the resulting users
            List<User> result = new ArrayList<>();

            // Process each received friend request
            while (resultSet.next()){
                secondStatement.setString(1, resultSet.getString(1));

                // Execute the second query to retrieve user details
                ResultSet resultSet1 = secondStatement.executeQuery();
                resultSet1.next();

                // Create a User object and add it to the result list
                User user = new User(resultSet1.getLong(1), resultSet1.getString(2), resultSet1.getString(3),
                        resultSet1.getString(4), resultSet1.getString(5), resultSet1.getString(6), resultSet1.getString(7));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
