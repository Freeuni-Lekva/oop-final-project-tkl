<%@ page import="DAOinterfaces.QuizScoresDao" %>
<%@ page import="Objects.Score" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Collections" %>
<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="DAOinterfaces.QuestionsDao" %>
<%@ page import="Objects.Questions.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.Duration" %>
<%@ page import="Objects.Questions.PictureResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);
    QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    QuestionsDao questionsDao = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);

    String scoreIdString = request.getParameter("score_id");
    if (scoreIdString == null) scoreIdString = (String) request.getSession().getAttribute("score_id");
    long scoreId = Long.parseLong(scoreIdString);

    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    String answersParam = request.getParameter("answers");
    String[] userAnswers = URLDecoder.decode(answersParam, "UTF-8").split(",");

    Quiz currentQuiz = quizDao.getQuizById(quizId);

    List<Question> questions = questionsDao.getQuizQuestions(quizId, currentQuiz.isQuestionsSorted());

    String[] correctAnswers = new String[questions.size()];
    boolean[] isCorrect = new boolean[questions.size()];
    for(int i = 0; i < questions.size(); i++){
        Question curQuestion = questions.get(i);
        correctAnswers[i] = curQuestion.getCorrectAnswers().get(0);
        isCorrect[i] = curQuestion.checkAnswer(userAnswers[i]);
    }

    Score score = quizScoresDao.getScore(scoreId);
    Duration duration = Duration.ofMillis(score.getTimeTaken());

%>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Result</title>
</head>
<body>

<style>

    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

    .quiz-container {
        padding: 5px;
        max-width: 800px;
        margin: 0 auto;
        text-align: center;
    }

    .result-container {
        max-width: 800px;
        margin: 0 auto;
        text-align: left;
    }

    .result-question {
        margin-bottom: 20px;
        padding: 15px;
        background-color: darkslategrey;
        border: 2px solid mediumaquamarine;
        border-radius: 50px;
    }

    .question img{
        height: 300px;
        width: 300px;
        border-radius: 50px;
    }

    .result-question h3 {
        color: white;
        margin: 0;
    }

    .result-answer {
        color: aquamarine;
        font-weight: bold;
    }

    .result-correct {
        color: green;
    }

    .result-wrong {
        color: red;
    }

</style>

<jsp:include page="navbar.jsp"></jsp:include>

<div class="result-container">
    <h1>Quiz Result</h1>
    <h3>Quiz Name: <%= currentQuiz.getQuizName() %></h3>

    <p>User's Score: <%= score.getScore() %></p>
    <p>Time Taken: <%= duration.toHours() %> hours, <%= (duration.toMinutes() % 60) %> minutes, <%= duration.toSeconds() % 60 %> seconds</p>

    <% for (int i = 0; i < correctAnswers.length; i++) { %>
    <div class="result-question">
        <h3>Question <%= i + 1 %>:</h3>
        <%if (questions.get(i) instanceof PictureResponse){ %>
        <img src="/images/<%= questions.get(i).getImageURL() %>" alt="Question Image"><br><br>
        <%}%>
        <p><%=questions.get(i).getQuestion()%></p>
        <p>User's Answer: <span class="result-answer"><%= userAnswers[i] %></span></p>
        <%String correctAnswer = "";
        for(int j = 0; j < questions.get(i).getCorrectAnswers().size(); j++) {
            if(j == questions.get(i).getCorrectAnswers().size() - 1) {
                correctAnswer += questions.get(i).getCorrectAnswers().get(j);
            } else {
                correctAnswer += questions.get(i).getCorrectAnswers().get(j) + ", ";
            }
        }%>
        <p><%=questions.get(i).getCorrectAnswers().size() == 1 ? "Correct Answer: " : "Correct Answers: "%><span class="result-answer"><%= correctAnswer %></span></p>
        <p>Result: <span class="<%= isCorrect[i] ? "result-correct" : "result-wrong" %>"><%= isCorrect[i] ? "Correct" : "Wrong" %></span></p>
    </div>
    <% } %>
</div>

</body>
</html>
