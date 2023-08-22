package DAOinterfaces;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface QuizCommentsDao {

    String ATTRIBUTE_NAME = "quizCommentsDao";

    /** function returns list of comments, which is filtered by received condition
     * function returns empty list, if there is no comment which meets received condition
     *
     * returned list will be sorted by time
     */
    List<Comment> getComments(String condition);


    /** function returns list of comments for received quiz
     * returns empty list if there is no comment
     *
     * returned list is sorted by creation time like getComments function
     */
    List<Comment> getQuizComments(long quizId);


    /** function tries to add new comment to DB, function returns true if comment adds successfully
     * false otherwise
     */
    boolean addComment(long quizId, long userId, String comment);

    /** function tries to remove comment from DB, function returns true if question removes successfully
     * false otherwise
     */
    boolean deleteComment(long commentId);
}
