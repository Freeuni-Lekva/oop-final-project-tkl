<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="DAOs.QuizSQL" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/20/2023
  Time: 8:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    List<Quiz> quizes = quizSQL.getQuizzes("");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <table>
        <tr>
            <th>Quiz ID</th>
            <th>Creator ID</th>
            <th>Description</th>
            <th>LINK</th>
        </tr>

        <%  for(Quiz quiz: quizes){ %>
            <tr>
                <td><%=quiz.getQuizId()%></td>
                <td><%=quiz.getCreatorId()%></td>
                <td><%=quiz.getDescription()%></td>
                <td><a href="quiz.jsp?quiz_id=<%=quiz.getQuizId()%>">Link</a></td>
            </tr>

        <% } %>

    </table>

</body>
</html>
