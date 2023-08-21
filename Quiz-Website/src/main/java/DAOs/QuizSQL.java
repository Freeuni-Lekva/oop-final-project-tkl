package DAOs;

import DAOinterfaces.QuizDao;
import Objects.Quiz;
import Objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizSQL implements QuizDao {

    private final BasicDataSource dataSource;

    public QuizSQL(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Quiz> getQuizzes(String condition) {

        String query = "SELECT *FROM quizzes";
        if(!condition.equals("")) query += condition;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            ResultSet resultSet = statement.executeQuery();
            List<Quiz> result = new ArrayList<>();

            while(resultSet.next()){
                Quiz newQuiz = new Quiz(resultSet.getLong(1), resultSet.getLong(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getTime(5), resultSet.getBoolean(6), resultSet.getBoolean(7), resultSet.getBoolean(8), resultSet.getBoolean(9));

                result.add(newQuiz);
            }

            resultSet.close();
            return result;

        } catch (SQLException ignored) {}

        return new ArrayList<>();
    }

    @Override
    public Quiz getQuizById(long id) {

        List<Quiz> result = getQuizzes(" WHERE id = " + id);

        if(result.size() == 1) return result.get(0);
        return null;
    }


    @Override
    public List<Quiz> getQuizzesByCreatorId(long id) {
        return getQuizzes(" WHERE creator_id = " + id);
    }

    @Override
    public boolean removeQuizById(long id) {

        if(getQuizById(id) == null) return false;
        String query = "DELETE FROM quizzes WHERE id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            return statement.executeUpdate() == 1;

        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public long addNewQuiz(User creator, String quizName, String description, boolean isDraft, boolean isPractice, boolean isSorted){

        if(creator == null) return ACCOUNT_NOT_FOUND;
        if(quizName.equals("") || description.equals("")) return NOT_ENOUGH_INFORMATION;

        String query = "INSERT INTO quizzes (creator_id, quiz_name, quiz_description, is_draft, is_practice, is_sorted, is_one_page) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, creator.getId());
            statement.setString(2, quizName);
            statement.setString(3, description);
            statement.setBoolean(4, isDraft);
            statement.setBoolean(5, isPractice);
            statement.setBoolean(6, isSorted);
            statement.setBoolean(7, true);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            long quizId = QUIZ_NOT_ADDED;
            if(resultSet.next()) quizId = resultSet.getLong(1);

            resultSet.close();
            return quizId;

        } catch (SQLException ignored) {}

        return SERVER_ERROR;
    }

    private boolean changeHelper(long id, String setOption, boolean setValue){

        Quiz quiz = getQuizById(id);
        if(quiz == null) return false;

        String query = "UPDATE quizzes SET " + setOption + " = ? WHERE id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBoolean(1, setValue);
            statement.setLong(2, id);

            return statement.executeUpdate() == 1;

        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean changeDraftStatus(long id, boolean isDraft) {
        return changeHelper(id, "is_draft", isDraft);
    }

    @Override
    public boolean changePracticeStatus(long id, boolean isPractice) {
        return changeHelper(id, "is_practice", isPractice);
    }

    @Override
    public boolean changeSortingStatus(long id, boolean isSorted) {
        return changeHelper(id, "is_sorted", isSorted);
    }
}
