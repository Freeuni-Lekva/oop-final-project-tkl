<%@ page import="Objects.Quiz" %>
<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="DAOinterfaces.QuizScoresDao" %>
<%@ page import="Objects.Score" %>
<%@ page import="java.time.Duration" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 8/21/2023
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    // Getting Current Quiz From Servlet Context
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) return;
    long quizId = Long.parseLong(quizIdString);
    QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    Quiz currentQuiz = quizDao.getQuizById(quizId);

    // Getting current user score and writing time
    QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);
    Long scoreId = (Long) request.getSession().getAttribute("CurrenQuizScoreID");
    Score score = quizScoresDao.getScore(scoreId);
    Duration duration = Duration.ofMillis(score.getTimeTaken());
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    .quiz-result-container{
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .quiz-result{
        display: flex;
        flex-direction: column;
        align-items: center;
        border: 2px solid mediumaquamarine;
        border-radius: 20px;
        padding-bottom: 20px;
        width: 500px;
    }
</style>
<html>
<head>
    <title>Quiz Result</title>
</head>
<body>
    <jsp:include page="navbar.jsp"></jsp:include>

    <div class="quiz-result-container">
        <div class="quiz-result">
            <h1>Quiz Result</h1>
            <h3>Quiz Name: <%= currentQuiz.getQuizName() %></h3>

            <p>User's Score: <%= score.getScore() %></p>
            <p>Time Taken: <%= duration.toHours() %> hours, <%= (duration.toMinutes() % 60) %> minutes, <%= duration.toSeconds() % 60 %> seconds</p>
            <a href="index.jsp"><button>Home Page</button></a>
        </div>
    </div>
</body>
</html>
