package DAOs;

import DAOinterfaces.QuizDao;
import Objects.Questions.MultipleChoice;
import Objects.Questions.QuestionResponse;
import Objects.Quiz;
import Objects.Questions.Question;
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
        List<Question> questions = testQuestions();

        List<Quiz> result = new ArrayList<>();
        Quiz t1 = new Quiz(1, 1, "Quiz 1", "First Test Quiz", new Date(), QUESTION_RESPONSE_CATEGORY, questions);
        Quiz t2 = new Quiz(2, 1, "Quiz 2", "Second Quiz", new Date(), QUESTION_RESPONSE_CATEGORY, questions);
        Quiz t3 = new Quiz(3, 1, "Quiz 3", "Third Quiz", new Date(), QUESTION_RESPONSE_CATEGORY, questions);

        result.add(t1);
        result.add(t2);
        result.add(t3);

        return result;
    }

    @Override
    public Quiz getQuizById(long id) {
        List<Question> questions = testQuestions();

        if(id == 1){
            return new Quiz(1, 1, "Quiz 1", "First Test Quiz", new Date(), QUESTION_RESPONSE_CATEGORY, questions);
        }else if(id == 2){
            return new Quiz(2, 1, "Quiz 1", "Second Quiz", new Date(), QUESTION_RESPONSE_CATEGORY, questions);
        }else if(id == 3){
            return new Quiz(3, 1, "Quiz 1", "Third Quiz", new Date(), QUESTION_RESPONSE_CATEGORY, questions);
        }

        return null;
    }

    //tmp method for testing
    private List<Question> testQuestions() {
        Question question = new QuestionResponse("vin dawera grafi monte-kristo", "kii, aq vcxovrob ra");
        List<Question> questions = new ArrayList<>();
        questions.add(question);

        String[] choices = new String[2];
        choices[0] = "ki";
        choices[1] = "ara";
        Question question1 = new MultipleChoice("romel wels moxda didgoris brdzola, 1121shi xom ara?", choices, 0);
        questions.add(question1);
        return questions;
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
