package Objects;

public class Score {
    private final long userId;
    private final long quizId;
    private final double score;
    private final long timeTaken;

    public Score(long userId, long quizId, double score, long timeTaken) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.timeTaken = timeTaken;
    }

    public long getUserId() {
        return userId;
    }

    public long getQuizId() {
        return quizId;
    }

    public double getScore() {
        return score;
    }

    public long getTimeTaken() {
        return timeTaken;
    }
}

