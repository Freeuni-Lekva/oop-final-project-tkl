package DAOs;

import DAOinterfaces.QuizScoresDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizScoresSQL implements QuizScoresDao {

    private final BasicDataSource dataSource;

    public QuizScoresSQL(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addNewScore(long userId, long quizId, double score, long startTime) {
        String query = "INSERT INTO quiz_scores (user_id, quiz_id, score, max_score, start_time) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE score = ?, max_score = ?, start_time = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            statement.setLong(2, quizId);
            statement.setDouble(3, score);
            statement.setDouble(4, Math.max(score, getMaxScore(quizId)));
            statement.setTimestamp(5, new Timestamp(startTime));

            // Set values for update part of ON DUPLICATE KEY UPDATE
            statement.setDouble(6, score);
            statement.setDouble(7, Math.max(score, getMaxScore(quizId)));
            statement.setTimestamp(8, new Timestamp(startTime));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getMaxScore(long quizId) {
        String query = "SELECT MAX(max_score) AS max_score FROM quiz_scores WHERE quiz_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("max_score");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Default value if no max_score is found
    }

    @Override
    public double getScore(long userId, long quizId) {
        String query = "SELECT score FROM quiz_scores WHERE user_id = ? AND quiz_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("score");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Default value if no score is found
    }

    @Override
    public long getTimeTaken(long userId, long quizId) {
        String query = "SELECT start_time, end_time FROM quiz_scores WHERE user_id = ? AND quiz_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Timestamp startTime = resultSet.getTimestamp("start_time");
                    Timestamp endTime = resultSet.getTimestamp("end_time");

                    if (startTime != null && endTime != null) {
                        return endTime.getTime() - startTime.getTime();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Default value if no start_time or end_time is found
    }

    @Override
    public List<Long> getUserIds(long quizId) {
        List<Long> userIds = new ArrayList<>();
        String query = "SELECT DISTINCT user_id FROM quiz_scores WHERE quiz_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    userIds.add(userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userIds;
    }

    @Override
    public List<Long> getQuizIds(long userId) {
        List<Long> quizIds = new ArrayList<>();
        String query = "SELECT DISTINCT quiz_id FROM quiz_scores WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long quizId = resultSet.getLong("quiz_id");
                    quizIds.add(quizId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizIds;
    }
}
