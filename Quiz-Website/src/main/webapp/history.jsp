<%--
  Created by IntelliJ IDEA.
  User: 99559
  Date: 20/08/2023
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="DAOinterfaces.QuizScoresDao" %>
<%@ page import="Objects.Score" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="java.time.Duration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);

    String userIdString = (String) request.getSession().getAttribute("MainUserID");
    long userId = Long.parseLong(userIdString);

    List<Long> quizIds = quizScoresDao.getQuizIds(userId);
    Map<Long, List<Double>> scoresMap = quizScoresDao.getScoresForUser(userId);
    Map<Long, List<Long>> timeTakenMap = quizScoresDao.getTimeTakenForUser(userId);
%>
<html>
<head>
    <title>Quiz History</title>
    <style>
        .history-container {
            text-align: center; /* Center align the contents */
        }

        table {
            margin: 0 auto;
            padding: 15px;
            border: 2px solid mediumaquamarine;
            border-radius: 50px;
        }

        table th, td {
            padding: 20px;
            border-bottom: 2px solid mediumaquamarine;
            border-radius: 25px;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="history-container">
    <h1>My History</h1>

    <%
        for (Long quizId : quizIds) {
            List<Double> scores = scoresMap.get(quizId);
            List<Long> timeTakenList = timeTakenMap.get(quizId);
    %>

    <h3>Quiz Name: <%= quizDao.getQuizById(quizId).getQuizName() %></h3>

    <table>
        <tr>
            <th>Score</th>
            <th>Time Taken</th>
        </tr>
        <% for (int i = 0; i < scores.size(); i++) { %>
        <tr>
            <td><%= scores.get(i) %></td>
            <%Duration duration = Duration.ofMillis(timeTakenList.get(i));%>
            <td><%= duration.toHours() %> hours, <%= (duration.toMinutes() % 60) %> minutes, <%= duration.toSeconds() % 60 %> seconds</td>
        </tr>
        <% } %>
    </table>

    <% } %>
</div>

</body>
</html>
