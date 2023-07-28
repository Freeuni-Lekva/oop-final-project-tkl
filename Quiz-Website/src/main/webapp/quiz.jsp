<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="Objects.Questions.*" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/20/2023
  Time: 8:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String quizIdString = request.getParameter("quiz_id");
    if(quizIdString == null)
        quizIdString = (String)request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

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
