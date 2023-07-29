<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="DAOs.UserSQL" %>
<%@ page import="DAOinterfaces.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/28/2023
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    UserDao userSQL = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

%>
<html>
<head>
    <title>Quiz Questions</title>
</head>
<body>

    <jsp:include page="navbar.jsp"/>

</body>
</html>
