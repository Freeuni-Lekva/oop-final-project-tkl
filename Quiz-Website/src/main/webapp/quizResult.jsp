<%@ page import="DAOinterfaces.QuizScoresDao" %>
<%@ page import="Objects.User" %><%--
  Created by IntelliJ IDEA.
  User: 99559
  Date: 15/08/2023
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    String userIdString = (String) request.getSession().getAttribute("MainUserID");
    long userId = Long.parseLong(userIdString);

    QuizScoresDao quizScoresDao = (QuizScoresDao) request.getServletContext().getAttribute(QuizScoresDao.ATTRIBUTE_NAME);
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>score: <%=quizScoresDao.getScore(userId, quizId)%></h1>
    <h1>time taken: <%=quizScoresDao.getTimeTaken(userId, quizId)%></h1>
</body>
</html>
