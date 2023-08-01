package DAOs;

import DAOinterfaces.QuestionsDao;
import Objects.Questions.MultipleChoice;
import Objects.Questions.PictureResponse;
import Objects.Questions.Question;
import Objects.Questions.QuestionResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;
import java.util.List;

public class QuestionsSQL implements QuestionsDao {

    private final BasicDataSource dataSource;

    public QuestionsSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Question> getQuizQuestionsForQuiz(long id) {
        List<Question> result = new ArrayList<>();
        Question q1 = new QuestionResponse(1, "who was joseph stalin?", new ArrayList<>());
        Question q2 = new MultipleChoice(2, "who wadsad", null, 0);
        Question q3 = new PictureResponse(3, "sulertia", "jasdasd", new ArrayList<>());
        result.add(q1);
        result.add(q2);
        result.add(q3);

        return result;
    }

    @Override
    public boolean addQuestion() {
        return false;
    }

    @Override
    public boolean deleteQuestion(long id) {
        return false;
    }
}
