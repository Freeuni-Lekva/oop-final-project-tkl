package DAOs;

import DAOinterfaces.QuizDao;
import Objects.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizSQL implements QuizDao {

    private final BasicDataSource dataSource;

    public QuizSQL(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Quiz> getQuizzes(String condition) {

        List<Quiz> result = new ArrayList<>();
        Quiz t1 = new Quiz(1, 1, "Quiz 1", "First Test Quiz", new Date(), QUESTION_RESPONSE_CATEGORY);
        Quiz t2 = new Quiz(2, 1, "Quiz 2", "Second Quiz", new Date(), QUESTION_RESPONSE_CATEGORY);
        Quiz t3 = new Quiz(3, 1, "Quiz 3", "Third Quiz", new Date(), QUESTION_RESPONSE_CATEGORY);

        result.add(t1);
        result.add(t2);
        result.add(t3);

        return result;
    }

    @Override
    public Quiz getQuizById(long id) {
        if(id == 1){
            return new Quiz(1, 1, "Quiz 1", "First Test Quiz", new Date(), QUESTION_RESPONSE_CATEGORY);
        }else if(id == 2){
            return new Quiz(2, 1, "Quiz 1", "Second Quiz", new Date(), QUESTION_RESPONSE_CATEGORY);
        }else if(id == 3){
            return new Quiz(3, 1, "Quiz 1", "Third Quiz", new Date(), QUESTION_RESPONSE_CATEGORY);
        }

        return null;
    }

    @Override
    public Quiz getQuizByCreatorId(long id) {
        return null;
    }

    @Override
    public boolean removeQuizById(long id) {
        return false;
    }
}
