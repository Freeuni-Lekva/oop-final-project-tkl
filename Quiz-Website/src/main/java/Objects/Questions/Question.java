package Objects.Questions;

public interface Question {

    boolean checkAnswer(String userAnswer);

    String getQuestion();

    long getId();
}
