<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/27/2023
  Time: 6:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>

    .error-message{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        font-size: 50px;
        padding-top: 150px;
    }

    .quiz-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .quiz-box {
        padding: 5px;
        background-color: mediumaquamarine;
        margin: 5px;
        width:  100%;
        border-radius: 50px;
        transition: background-color 0.3s;
        display: flex;
        justify-content: center;
    }

    .quiz-box h2, p{
        color: white;
        margin-top: 0;
    }

    .red-message{
        color: red;
    }

    .quiz-box:hover {
        background-color: aquamarine;
        cursor: pointer;
    }

    .inner-quiz-box{
        display: flex;
        flex-direction: column;
        align-items: center;
    }
</style>

<%
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    String mainUserId = (String) request.getSession().getAttribute("MainUserID");

%>
<html>
<head>
    <title>Create Quiz</title>
</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <% if(mainUserId == null) { %>
        <p class="error-message">Only Members Of TKL Can Create Quizzes</p>
    <% } %>
    <% if(mainUserId != null) { %>
        <div class="quiz-container">
            <a href="createNewQuiz.jsp"><button>Create New Quiz</button></a>
            <% for(Quiz quiz: quizSQL.getQuizzesByCreatorId(Long.parseLong(mainUserId))){ %>
                <div class="quiz-box" onclick="goToQuiz(<%= quiz.getQuizId()%>)">
                    <div class="inner-quiz-box">
                        <h2><%=quiz.getQuizName()%></h2>
                        <% if(quiz.isDraft()) { %>
                            <p class="red-message">Draft Quiz</p>
                        <% }else{ %>
                            <p>Active Quiz</p>
                        <% } %>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>
    <script>
        function goToQuiz(quizId){
            window.location.href = "editQuizQuestions.jsp?quiz_id=" + quizId;
        }
    </script>

</body>
</html>
