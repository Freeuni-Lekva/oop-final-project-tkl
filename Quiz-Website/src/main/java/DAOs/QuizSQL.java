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
    List<Quiz> quizzes = new ArrayList<>();
    public QuizSQL(BasicDataSource dataSource) {

        this.dataSource = dataSource;

        Quiz t1 = new Quiz(1, 17, "Quiz 1", "First Test Quiz", new Date(), false, false);
        Quiz t2 = new Quiz(2, 17, "Quiz 2", "Second Quiz", new Date(), false, false);
        Quiz t3 = new Quiz(3, 17, "Quiz 3", "Third Quiz", new Date(), true, false);

        quizzes.add(t1);
        quizzes.add(t2);
        quizzes.add(t3);
    }


    @Override
    public List<Quiz> getQuizzes(String condition) {

        return quizzes;
    }

    @Override
    public Quiz getQuizById(long id) {
        if(id < 0 || id > quizzes.size()) return null;
        return quizzes.get((int) id);
    }


    @Override
    public List<Quiz> getQuizzesByCreatorId(long id) {
        List<Quiz> result = new ArrayList<>();
        for(Quiz quiz:quizzes){
            if(quiz.getCreatorId() == id) result.add(quiz);
        }

        return result;
    }

    @Override
    public boolean removeQuizById(long id) {
        return false;
    }

    @Override
    public int addNewQuiz(long creatorId, String quizName, String description, Date createTime, boolean isDraft, boolean isPractice){
        Quiz quiz = new Quiz(quizzes.size() + 1, creatorId, quizName, description, createTime, isDraft, isPractice);
        quizzes.add(quiz);
        return quizzes.size();
    }
}
