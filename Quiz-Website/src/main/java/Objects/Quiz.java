package Objects;

import java.util.Date;

public class Quiz {

    private final long quizId;
    private final long creatorId;
    private final String quizName;
    private final String description;
    private final Date createTime;
    private final boolean isDraft;
    private final boolean isPractice;
    private final boolean isQuestionsSorted;

    public Quiz(long quizId, long creatorId, String quizName, String description, Date createTime, boolean isDraft, boolean isPractice, boolean isQuestionsSorted) {
        this.quizId = quizId;
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.createTime = createTime;
        this.isDraft = isDraft;
        this.isPractice = isPractice;
        this.isQuestionsSorted = isQuestionsSorted;
    }

    public long getQuizId(){
        return quizId;
    }
    public long getCreatorId(){ return creatorId;}
    public String getQuizName(){
        return quizName;
    }
    public String getDescription(){
        return description;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public boolean isDraft(){
        return isDraft;
    }
    public boolean isPractice() { return isPractice; }
    public boolean isQuestionsSorted() { return isQuestionsSorted; }
}
