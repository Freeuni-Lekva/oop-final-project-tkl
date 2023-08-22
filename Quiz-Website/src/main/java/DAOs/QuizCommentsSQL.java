package DAOs;

import DAOinterfaces.QuizCommentsDao;
import Objects.QuizComment;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class QuizCommentsSQL implements QuizCommentsDao {

    private final BasicDataSource dataSource;

    public QuizCommentsSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<QuizComment> getComments(String condition) {
        return null;
    }

    @Override
    public List<QuizComment> getQuizComments(long quizId) {
        return null;
    }

    @Override
    public boolean addComment(long quizId, long userId, String comment) {
        return false;
    }

    @Override
    public boolean deleteComment(long commentId) {
        return false;
    }
}
