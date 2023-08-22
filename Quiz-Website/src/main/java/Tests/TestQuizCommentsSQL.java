package Tests;

import DAOinterfaces.QuizCommentsDao;
import DAOs.QuizCommentsSQL;
import Objects.QuizComment;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.xml.stream.events.Comment;
import java.util.List;

public class TestQuizCommentsSQL extends TestCase {

    public void testAllFunctions(){

        ServerConfigurations.clearTable("quiz_comments");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();
        QuizCommentsDao quizCommentsSQL = new QuizCommentsSQL(dataSource);

        // Checking if getComments function returns empty list
        List<QuizComment> comments = quizCommentsSQL.getComments("");
        assertEquals(comments.size(), 0);

        // Adding few comments to DB, not using real IDs for user and quiz, it not necessary for testing
        boolean r1 = quizCommentsSQL.addComment(1, 1, "First Comment | First Quiz");
        boolean r2 = quizCommentsSQL.addComment(1, 2, "Second Comment | First Quiz");
        boolean r3 = quizCommentsSQL.addComment(2, 1, "First Comment | Second Quiz");

        // Checking if every question added correctly
        comments = quizCommentsSQL.getComments("");
        assertEquals(comments.size(), 3);
        assert(r1);
        assert(r2);
        assert(r3);

        // Testing if QuizComments object works correctly
        comments = quizCommentsSQL.getQuizComments(1);
        assertEquals(comments.size(), 2);
        assertEquals(comments.get(0).getQuizCommentText(), "First Comment | First Quiz");
        assertEquals(comments.get(0).getQuizCommentUserId(), 1);
        assertEquals(comments.get(0).getQuizCommentQuizId(), 1);

        // Calling few more functions, just for 100% coverage
        comments.get(0).getQuizCommentId();
        comments.get(0).getQuizCommentTime();


        // Testing deleting function
        quizCommentsSQL.deleteComment(comments.get(0).getQuizCommentId());
        comments = quizCommentsSQL.getQuizComments(1);
        assertEquals(comments.size(), 1);
    }
}
