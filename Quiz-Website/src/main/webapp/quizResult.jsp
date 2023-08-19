<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.Duration" %>
<%@ page import="Objects.User" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="DAOinterfaces.QuizScoresDao" %>
<%@ page import="Objects.Questions.Question" %>
<%@ page import="Objects.Questions.MultipleChoice" %>
<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="DAOinterfaces.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    String userIdString = (String) request.getSession().getAttribute("MainUserID");
    long userId = Long.parseLong(userIdString);

    QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);
    QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

    List<Long> userIds = quizScoresDao.getUserIds(quizId);
    Map<Long, Double> scores = quizScoresDao.getScoresForQuiz(quizId);
    Map<Long, Long> timeTakenMap = quizScoresDao.getTimeTakenForQuiz(quizId);
    Quiz quiz = quizDao.getQuizById(quizId);
%>

<html>
<head>
    <title>Quiz Scores</title>

    <style>
        .user-score {
            color: red;
        }
    </style>

</head>
<body>


<h1>Quiz Scores - <%= quiz.getQuizName() %></h1>
<%boolean isCompleted = false;%>

<table border="1">
    <tr>
        <th>User Name</th>
        <th>Score</th>
        <th>Time Taken</th>
    </tr>
    <% for (Long curUserId : userIds) {
        Double score = scores.get(curUserId);
        Duration duration = Duration.ofMillis(timeTakenMap.get(curUserId));
        boolean isCurrentUser = curUserId == userId;
        if (isCurrentUser)
            isCompleted = true;
    %>
    <tr>
        <td <%= isCurrentUser ? "class=\"user-score\"" : "" %>><%= userDao.getUserById(curUserId).getName() %></td>
        <td <%= isCurrentUser ? "class=\"user-score\"" : "" %>><%= score %></td>
        <td <%= isCurrentUser ? "class=\"user-score\"" : "" %>><%= duration.toHours() + " hours, " + (duration.toMinutes() % 60) + " minutes, " + (duration.toSeconds() % 60) + " seconds" %></td>
    </tr>
    <% } %>
</table>

<% if (isCompleted) { %>
<form action="correctAnswers.jsp" method="get">
    <input type="hidden" name="quiz_id" value="<%= quizId %>">
    <input type="submit" value="View Correct Answers">
</form>
<% } %>

</body>
</html>
