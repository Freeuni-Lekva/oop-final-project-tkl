package Objects;

import java.util.Date;

public class Quiz {

    private final long quizId;
    private final long creatorId;
    private final String quizName;
    private final String description;
    private final Date createTime;
    private final int category;


    public Quiz(int quizId, int creatorId,String quizName, String description, Date createTime, int category) {
        this.quizId = quizId;
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.createTime = createTime;
        this.category = category;
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

    public int getCategory(){
        return category;
    }
}
