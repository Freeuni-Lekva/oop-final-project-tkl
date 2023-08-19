package DAOs;

import DAOinterfaces.QuizScoresDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizScoresSQL implements QuizScoresDao {

    private final BasicDataSource dataSource;

    public QuizScoresSQL(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addNewScore(long userId, long quizId, double score, long startTime) {
        String query = "INSERT INTO quiz_scores (user_id, quiz_id, score, max_score, start_time) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE score = ?, max_score = ?, start_time = ?, end_time = ?";

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
            statement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

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
    public Map<Long, Double> getScoresForQuiz(long quizId) {
        Map<Long, Double> scoresMap = new HashMap<>();
        String query = "SELECT user_id, score FROM quiz_scores WHERE quiz_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    double score = resultSet.getDouble("score");
                    scoresMap.put(userId, score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoresMap;
    }

    @Override
    public Map<Long, Long> getTimeTakenForQuiz(long quizId) {
        Map<Long, Long> timeTakenMap = new HashMap<>();
        String query = "SELECT user_id, start_time, end_time FROM quiz_scores WHERE quiz_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    Timestamp startTime = resultSet.getTimestamp("start_time");
                    Timestamp endTime = resultSet.getTimestamp("end_time");
                    if (startTime != null && endTime != null) {
                        long timeTaken = endTime.getTime() - startTime.getTime();
                        timeTakenMap.put(userId, timeTaken);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeTakenMap;
    }

}
