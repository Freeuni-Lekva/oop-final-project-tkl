package DAOs;

import DAOinterfaces.QuizScoresDao;
import Objects.Score;
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
    public long addNewScore(long userId, long quizId, double score, long startTime) {
        String query = "INSERT INTO quiz_scores (user_id, quiz_id, score, start_time) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, userId);
            statement.setLong(2, quizId);
            statement.setDouble(3, score);
            statement.setTimestamp(4, new Timestamp(startTime));

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating score entry failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return a default value in case of failure
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
    public List<Score> getBestScoresAndTimesForQuiz(long quizId) {
        List<Score> scores = new ArrayList<>();
        String query = "SELECT user_id, quiz_id, score, end_time, start_time\n" +
                "FROM (\n" +
                "         SELECT\n" +
                "             t1.user_id,\n" +
                "             t1.quiz_id,\n" +
                "             t1.score,\n" +
                "             t1.end_time,\n" +
                "             t1.start_time,\n" +
                "             ROW_NUMBER() OVER (PARTITION BY t1.user_id, t1.quiz_id, t1.score ORDER BY t1.score DESC, t1.end_time - t1.start_time) AS rn\n" +
                "         FROM quiz_scores t1\n" +
                "                  JOIN (\n" +
                "             SELECT user_id, quiz_id, MAX(score) AS max_score\n" +
                "             FROM quiz_scores\n" +
                "             WHERE quiz_id = ?\n" +
                "             GROUP BY user_id, quiz_id\n" +
                "         ) t2 ON t1.user_id = t2.user_id AND t1.quiz_id = t2.quiz_id AND t1.score = t2.max_score\n" +
                "     ) ranked\n" +
                "WHERE rn = 1\n" +
                "ORDER BY score DESC, end_time - start_time";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    double maxScore = resultSet.getDouble("score");
                    Timestamp start_time = resultSet.getTimestamp("start_time");
                    Timestamp end_time = resultSet.getTimestamp("end_time");
                    long timeTaken = end_time.getTime() - start_time.getTime();

                    Score score = new Score(userId, quizId, maxScore, timeTaken);
                    scores.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
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

    @Override
    public Map<Long, List<Double>> getScoresForUser(long userId) {
        Map<Long, List<Double>> scoresMap = new HashMap<>();
        String query = "SELECT quiz_id, score FROM quiz_scores WHERE user_id = ? ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long quizId = resultSet.getLong("quiz_id");
                    double score = resultSet.getDouble("score");
                    scoresMap.computeIfAbsent(quizId, k -> new ArrayList<>()).add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoresMap;
    }


    @Override
    public Map<Long, List<Long>> getTimeTakenForUser(long userId) {
        Map<Long, List<Long>> timeTakenMap = new HashMap<>();
        String query = "SELECT quiz_id, start_time, end_time FROM quiz_scores WHERE user_id = ? ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long quizId = resultSet.getLong("quiz_id");
                    Timestamp startTime = resultSet.getTimestamp("start_time");
                    Timestamp endTime = resultSet.getTimestamp("end_time");
                    if (startTime != null && endTime != null) {
                        long timeTaken = endTime.getTime() - startTime.getTime();
                        timeTakenMap.computeIfAbsent(quizId, k -> new ArrayList<>()).add(timeTaken);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeTakenMap;
    }

    @Override
    public Score getScore(long id) {
        String query = "SELECT user_id, quiz_id, score, end_time, start_time FROM quiz_scores WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    long quizId = resultSet.getLong("quiz_id");
                    double maxScore = resultSet.getDouble("score");
                    Timestamp start_time = resultSet.getTimestamp("start_time");
                    Timestamp end_time = resultSet.getTimestamp("end_time");
                    long timeTaken = end_time.getTime() - start_time.getTime();

                    return new Score(userId, quizId, maxScore, timeTaken);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
