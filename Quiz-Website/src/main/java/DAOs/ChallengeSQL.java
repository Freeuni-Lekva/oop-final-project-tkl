package DAOs;

import DAOinterfaces.ChallengeDao;
import Objects.Challenge;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengeSQL implements ChallengeDao {

    private final BasicDataSource dataSource;

    public ChallengeSQL(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean sendChallenge(Challenge challenge) {
        String query = "INSERT INTO challenge (sender_id, receiver_id, quiz_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, challenge.getSenderId());
            statement.setLong(2, challenge.getReceiverId());
            statement.setLong(3, challenge.getQuizId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Challenge getChallenge(long id) {
        String query = "SELECT * FROM challenge WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long senderId = resultSet.getLong("sender_id");
                    long receiverId = resultSet.getLong("receiver_id");
                    long quizId = resultSet.getLong("quiz_id");
                    Timestamp timestamp = resultSet.getTimestamp("send_time");

                    return new Challenge(id, senderId, receiverId, quizId, timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Challenge> getChallengesForUser(long userId) {
        String query = "SELECT * FROM challenge WHERE sender_id = ? OR receiver_id = ?";

        List<Challenge> challenges = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            statement.setLong(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    long senderId = resultSet.getLong("sender_id");
                    long receiverId = resultSet.getLong("receiver_id");
                    long quizId = resultSet.getLong("quiz_id");
                    Timestamp timestamp = resultSet.getTimestamp("send_time");

                    challenges.add(new Challenge(id, senderId, receiverId, quizId, timestamp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return challenges;
    }

    @Override
    public boolean removeChallenge(long id) {
        String query = "DELETE FROM challenge WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
