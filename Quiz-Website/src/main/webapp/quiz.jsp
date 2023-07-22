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
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

        * {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', sans-serif;
            font-weight: 500;
            font-size: 15px;
            color: white;
            text-decoration: none;
            background-color: darkslategrey;
        }

        h1 {
            text-align: center;
            margin-top: 50px;
        }

        a {
            display: block;
            text-align:center;
            margin-top:50px;
            font-family: 'Roboto', sans-serif;
            font-weight: 500;
            font-size: 20px;
            color: white;
            text-decoration: none;
        }

    </style>
</head>
<body>

<h1><%=currentQuiz.getQuizName()%></h1>

<br>
<a href="sendChallenge.jsp?quiz_id=<%=currentQuiz.getQuizId()%>">Send Challenge</a>
<a href="quizes.jsp?quiz_id=<%=quizId%>">Go Back</a>


</body>
</html>
