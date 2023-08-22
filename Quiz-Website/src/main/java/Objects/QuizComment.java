package Objects;

import java.util.Date;

public class QuizComment {

    private final long quizCommentId;
    private final long userId;
    private final long quizId;
    private final String commentText;
    private final Date commentTime;


    public QuizComment(long quizCommentId, long userId, long quizId, String commentText, Date commentTime){
        this.quizCommentId = quizCommentId;
        this.userId = userId;
        this.quizId = quizId;
        this.commentText = commentText;
        this.commentTime = commentTime;
    }

    public long getQuizCommentId() { return quizCommentId; }
    public long getQuizCommentUserId() { return userId; }
    public long getQuizCommentQuizId() { return quizId; }
    public String getQuizCommentText() { return commentText; }
    public Date getQuizCommentTime() { return commentTime; }
}
