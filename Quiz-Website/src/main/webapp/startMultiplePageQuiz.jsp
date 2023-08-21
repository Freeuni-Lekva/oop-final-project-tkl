<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 8/21/2023
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);

    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    Quiz current = quizSQL.getQuizById(quizId);
%>

<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
