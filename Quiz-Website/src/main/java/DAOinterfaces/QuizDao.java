package DAOinterfaces;

import Objects.Quiz;

import java.util.List;

public interface QuizDao {

    int QUESTION_RESPONSE_CATEGORY = 1;
    int FILL_BLANK_CATEGORY = 2;
    int MULTIPLE_CHOICES_CATEGORY = 3;
    int PICTURE_RESPONSE_CATEGORY = 4;

    String ATTRIBUTE_NAME = "quizDao";

    List<Quiz> getQuizzes(String condition);

    Quiz getQuizById(long id);

    Quiz getQuizByCreatorId(long id);

    boolean removeQuizById(long id);
}
