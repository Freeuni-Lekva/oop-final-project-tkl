package DAOs;

import DAOinterfaces.QuestionsDao;
import Objects.Questions.MultipleChoice;
import Objects.Questions.PictureResponse;
import Objects.Questions.Question;
import Objects.Questions.QuestionResponse;
import Objects.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionsSQL implements QuestionsDao {

    private final BasicDataSource dataSource;

    public QuestionsSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    /** function tries to get multiple-choice-type question from DB, which question_id equals to received argument
     * function returns null if there is no such questions
     */
    private Question getMultipleChoiceQuestion(long id, String question){

        String query = "SELECT *FROM multiple_choice_answers WHERE question_id = " + id;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement questionsStatement = connection.prepareStatement(query);){

            ResultSet resultSet = questionsStatement.executeQuery();
            List<String> possibleAnswers = new ArrayList<>();
            List<Integer> correctIndex = new ArrayList<>();

            while (resultSet.next()){
                if(resultSet.getBoolean(4)) correctIndex.add(possibleAnswers.size());
                possibleAnswers.add(resultSet.getString(3));
            }

            resultSet.close();

            if(possibleAnswers.size() == 0) return null;
            return new MultipleChoice(id, question, possibleAnswers, correctIndex);

        }catch (SQLException ignored) { }
        return null;
    }

    /** function tries to get question from DB, which question_id equals to received argument
     * function returns null if there is no such questions
     */
    private Question getSimpleQuestion(long id, String question, String image){

        String query = "SELECT *FROM simple_answers WHERE question_id = " + id;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement questionsStatement = connection.prepareStatement(query);){

            ResultSet resultSet = questionsStatement.executeQuery();
            List<String> answers = new ArrayList<>();

            while (resultSet.next()){
                String answer = resultSet.getString(3);
                answers.add(answer);
            }

            resultSet.close();
            if(answers.size() == 0) return null;

            if(image == null) return new QuestionResponse(id, question, answers);
            return new PictureResponse(id, image, question, answers);

        }catch (SQLException ignored) { }
        return null;
    }

    @Override
    public List<Question> getQuestions(String condition) {

        String query = "SELECT *FROM questions";
        if(!condition.equals("")) query += condition;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            ResultSet resultSet = statement.executeQuery();
            List<Question> result = new ArrayList<>();

            while(resultSet.next()){
                long id = resultSet.getLong(1);
                int type = resultSet.getInt(3);
                String question = resultSet.getString(4);
                String image = resultSet.getString(5);

                Question newQuestion;

                if(type == QuestionsDao.MULTIPLE_CHOICES_TYPE) newQuestion = getMultipleChoiceQuestion(id, question);
                    else newQuestion = getSimpleQuestion(id, question, image);

                if(newQuestion != null) result.add(newQuestion);
            }

            resultSet.close();
            return result;

        } catch (SQLException ignored) {}

        return new ArrayList<>();
    }

    @Override
    public Question getQuestionById(long id) {

        List<Question> questions = getQuestions(" WHERE id = " + id);

        if(questions.size() == 1) return questions.get(0);
        return null;
    }

    @Override
    public List<Question> getQuizQuestions(long id) {
        return getQuestions(" WHERE quiz_id = " + id);
    }

    /** helper function which adds only question to DB
     * function returns newly added questions id, if anything goes wrong function returns -1
     */
    private long addQuestion(long quizId, int type, String question, String image){

        String query = "INSERT INTO questions (quiz_id, question_type, question_text, question_image) VALUES(?, ?, ?, ?)";

        // Trying to get connection from dataSource
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            // Setting values to prepared statement
            statement.setLong(1, quizId);
            statement.setInt(2, type);
            statement.setString(3, question);
            statement.setString(4, image);

            // Execute statement
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();


            if(!resultSet.next()) return -1;
            long id = resultSet.getLong(1);

            resultSet.close();
            return id;

        }catch (SQLException ignored) { }
        return -1;
    }

    /** helper function which tries to add new answers to DB,
     * if something goes wrong function returns false
     */
    private boolean addAnswers(boolean isMultipleType, long questionId, List<String> answers, List<Integer> correctAnswers){

        try(Connection connection = dataSource.getConnection()){

            for(int i = 0; i < answers.size(); i++){

                // Creating query string according to received argument
                String query = "INSERT INTO multiple_choice_answers (question_id, answer, is_correct) VALUES (?, ? ,?)";
                if(!isMultipleType) query = "INSERT INTO simple_answers (question_id, correct_answer) VALUES (? ,?)";

                PreparedStatement statement = connection.prepareStatement(query);

                statement.setLong(1, questionId);
                statement.setString(2, answers.get(i));

                // Setting addition value to query if addAnswers function called for multiple choice type
                if(isMultipleType){
                    statement.setBoolean(3, correctAnswers.contains(i));
                }

                statement.executeUpdate();
                statement.close();

            }

        }catch (SQLException ignored) {}
        return false;
    }



    @Override
    public boolean addSimpleQuestions(Quiz quiz, String question, List<String> answers, String imageURL) {

        if(quiz == null) return false;

        long id = addQuestion(quiz.getQuizId(), imageURL == null ? QuestionsDao.QUESTION_RESPONSE_TYPE : QuestionsDao.PICTURE_RESPONSE_TYPE, question, imageURL);
        if(id == -1) return false;

        return addAnswers(false, id, answers, null);
    }

    @Override
    public boolean addMultipleChoiceQuestions(Quiz quiz, String question, List<String> possibleAnswers, List<Integer> correctAnswers) {

        if(quiz == null) return false;

        long id = addQuestion(quiz.getQuizId(),  QuestionsDao.MULTIPLE_CHOICES_TYPE, question, null);
        if(id == -1) return false;

        return addAnswers(true, id, possibleAnswers, correctAnswers);
    }

    @Override
    public boolean deleteQuestion(long id) {

        // If there is no question with received id function returns false
        if(getQuestionById(id) == null) return false;
        String query = "DELETE FROM questions WHERE id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            return statement.executeUpdate() == 1;

        } catch (SQLException ignored) {}
        return false;
    }
}
