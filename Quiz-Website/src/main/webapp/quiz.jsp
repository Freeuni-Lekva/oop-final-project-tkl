<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/20/2023
  Time: 8:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    long quizId = Long.parseLong(request.getParameter("quiz_id"));
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    Quiz currentQuiz = quizSQL.getQuizById(quizId);
%>
<html>
<head>
    <title><%=currentQuiz.getQuizName()%></title>
</head>
<body>



</body>
</html>
