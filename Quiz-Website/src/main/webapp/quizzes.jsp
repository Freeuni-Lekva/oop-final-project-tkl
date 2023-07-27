<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="DAOs.QuizSQL" %>
<%@ page import="DAOinterfaces.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/20/2023
  Time: 8:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    UserDao userSQL = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
    List<Quiz> quizzes = quizSQL.getQuizzes("");
%>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

    body {
        font-family: "Roboto", sans-serif;
    }

    .quiz-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .quiz-box {
        padding: 5px;
        background-color: mediumaquamarine;
        margin: 15px;
        width:  calc(33.33% - 30px);
        border-radius: 10px;
        transition: background-color 0.3s;
    }

    .quiz-box h2 {
        color: white;
        margin-top: 0;
    }

    .quiz-box p {
        color: white;
    }

    .quiz-box:hover {
        background-color: aquamarine;
        cursor: pointer;
    }

</style>

<html>
<head>
    <title>Quizzes</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <div class="quiz-container">
        <% for(Quiz quiz: quizzes){ %>
            <% if(quiz.isDraft()) continue;%>
            <div class="quiz-box" onclick="goToQuiz(<%= quiz.getQuizId()%>)">
                <h2><%=quiz.getQuizName()%></h2>
                <p><strong>Creator: </strong><%=userSQL.getUserById(quiz.getCreatorId()).getName()%></p>
                <p><%=quiz.getDescription()%></p>
            </div>
        <% } %>
    </div>
    <script>
        function goToQuiz(quizId){
            window.location.href = "quiz.jsp?quiz_id=" + quizId;
        }
    </script>
</body>
</html>
