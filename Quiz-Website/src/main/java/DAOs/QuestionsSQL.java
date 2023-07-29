package DAOs;

import DAOinterfaces.QuestionsDao;
import Objects.Questions.Question;

import java.util.List;

public class QuestionsSQL implements QuestionsDao {

    @Override
    public List<Question> getQuizQuestionsForQuiz(long id) {
        return null;
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
