package DAOs;

import DAOinterfaces.QuestionsDao;
import Objects.Questions.Question;
import Objects.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QuestionsSQL implements QuestionsDao {

    private final BasicDataSource dataSource;

    public QuestionsSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Question> getQuizQuestions(long id) {
        return null;
    }

    @Override
    public boolean addSimpleQuestions(Quiz quiz, String question, List<String> answers, String imageURL) {
        return false;
    }

    @Override
    public boolean addMultipleChoiceQuestions(Quiz quiz, String question, List<String> possibleAnswers, List<Integer> correctAnswers) {
        return false;
    }

    @Override
    public boolean deleteQuestion(long id) {
        return false;
    }
}
