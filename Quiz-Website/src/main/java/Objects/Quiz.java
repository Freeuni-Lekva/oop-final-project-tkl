package Objects;

import Objects.Questions.Question;

import java.util.Date;
import java.util.List;

public class Quiz {

    private final long quizId;
    private final long creatorId;
    private final String quizName;
    private final String description;
    private final Date createTime;
    private final boolean isDraft;
    private final List<Question> questions;


    public Quiz(int quizId, int creatorId, String quizName, String description, Date createTime, boolean isDraft, List<Question> questions) {
        this.quizId = quizId;
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.createTime = createTime;
        this.isDraft = isDraft;
        this.questions = questions;
    }

    public long getQuizId(){
        return quizId;
    }

    public long getCreatorId(){
        return creatorId;
    }

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

    public List<Question> getQuestions() { return questions; }
}
