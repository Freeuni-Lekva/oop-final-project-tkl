package DAOs;

import DAOinterfaces.FriendsDao;
import Objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsSQL implements FriendsDao {
    private final BasicDataSource dataSource;

    // Constructor
    public FriendsSQL(BasicDataSource dataSource){this.dataSource = dataSource;}

    // Retrieves a list of friends for a given user
    @Override
    public List<User> getUserFriends(Long user_id) {
        try {
            // Establish a database connection
            Connection connection = dataSource.getConnection();

            // Prepare the SQL query to retrieve friend names
            String query1 = "select first_user_id from friendships where second_user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setLong(1, user_id);

            // Execute the query to retrieve friend names
            ResultSet resultSet = preparedStatement.executeQuery();

            // Create a list to store the resulting users
            List<User> result = new ArrayList<>();

            // Prepare the SQL query to retrieve user details
            String query2 = "select * from users where id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);

            // Process each friend
            while (resultSet.next()) {
                preparedStatement1.setLong(1, resultSet.getLong(1));

                // Execute the query to retrieve user details
                ResultSet resultSet1 = preparedStatement1.executeQuery();
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

    private PreparedStatement prepareStatement(String query, long val1, long val2){

        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement result = connection.prepareStatement(query);
            result.setLong(1, val1);
            result.setLong(2, val2);

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Adds a friendship between two users
    @Override
    public int addFriendship(Long user_id1, Long user_id2) {
        if (checkIfFriends(user_id1, user_id2)) return FAILED_ADDED;

        try {
            PreparedStatement preparedStatement1 = prepareStatement("INSERT INTO friendships(first_user_id,second_user_id) VALUES(?,?)", user_id1, user_id2);
            PreparedStatement preparedStatement2 = prepareStatement("INSERT INTO friendships(first_user_id,second_user_id) VALUES(?,?)", user_id2, user_id1);

            // Execute the queries and check if any rows were affected
            if(preparedStatement2.executeUpdate() != 0 && preparedStatement1.executeUpdate() != 0) return SUCCESS_ADDED;
        } catch (SQLException e) {
            return FAILED_ADDED;
        }
        return FAILED_ADDED;
    }

    // Removes a friendship between two users
    @Override
    public int removeFriendship(Long user_id1, Long user_id2) {
        try {

            PreparedStatement preparedStatement1 = prepareStatement("delete from friendships where first_user_id = ? and second_user_id = ?", user_id1, user_id2);
            PreparedStatement preparedStatement2 = prepareStatement("delete from friendships where (first_user_id = ? and second_user_id = ?)", user_id2, user_id1);

            if(preparedStatement1.executeUpdate() != 0 && preparedStatement2.executeUpdate() != 0) return SUCCESS_REMOVED;

        } catch (SQLException e) {
            return FAILED_REMOVED;
        }
        return FAILED_REMOVED;
    }

    // Checks if two users are friends
    @Override
    public boolean checkIfFriends(Long user_id1, Long user_id2) {
        try {

            PreparedStatement preparedStatement1 = prepareStatement("select * from friendships where first_user_id = ? and second_user_id = ?", user_id1, user_id2);
            PreparedStatement preparedStatement2 = prepareStatement("select * from friendships where first_user_id = ? and second_user_id = ?", user_id2, user_id1);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            return resultSet1.next() && resultSet2.next();
        } catch (SQLException e) {
            return false;
        }
    }
}
