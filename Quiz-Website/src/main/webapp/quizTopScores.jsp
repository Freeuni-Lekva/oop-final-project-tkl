<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.Duration" %>
<%@ page import="DAOinterfaces.QuizScoresDao" %>
<%@ page import="DAOinterfaces.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    String userIdString = (String) request.getSession().getAttribute("MainUserID");
    long userId = Long.parseLong(userIdString);

    QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);
    UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

    List<Long> userIds = quizScoresDao.getUserIds(quizId);
    Map<Long, Double> scores = quizScoresDao.getScoresForQuiz(quizId);
    Map<Long, Long> timeTakenMap = quizScoresDao.getTimeTakenForQuiz(quizId);
%>

<style>
    .user-score{
        color: aquamarine;
    }

    table{
        padding: 15px;
        border: 2px solid mediumaquamarine;
        border-radius: 50px;
    }

    table th, td{
        padding: 20px;
        border-bottom: 2px solid mediumaquamarine;
        border-radius: 25px;
    }
</style>

<h1>Top Scores</h1>
<table>
    <tr>
        <th>User Name</th>
        <th>Score</th>
        <th>Time Taken</th>
    </tr>
    <% for (Long curUserId : userIds) {
        Double score = scores.get(curUserId);
        Duration duration = Duration.ofMillis(timeTakenMap.get(curUserId));
        boolean isCurrentUser = curUserId == userId;
    %>
    <tr>
        <td <%= isCurrentUser ? "class=\"user-score\"" : "" %>><%= userDao.getUserById(curUserId).getName() %></td>
        <td <%= isCurrentUser ? "class=\"user-score\"" : "" %>><%= score %></td>
        <td <%= isCurrentUser ? "class=\"user-score\"" : "" %>><%= duration.toHours() + " hours, " + (duration.toMinutes() % 60) + " minutes, " + (duration.toSeconds() % 60) + " seconds" %></td>
    </tr>

    <% } %>
</table>