<%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 8/3/2023
  Time: 11:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="Objects.Questions.Question" %>
<%@ page import="Objects.Questions.QuestionResponse" %>
<%@ page import="Objects.Questions.MultipleChoice" %>
<%@ page import="Objects.Questions.PictureResponse" %>
<%@ page import="DAOinterfaces.QuestionsDao" %>

<%
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    Quiz currentQuiz = quizSQL.getQuizById(quizId);

    QuestionsDao questionsSQL = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);
    List<Question> questions = questionsSQL.getQuizQuestions(quizId);
%>

<style>

    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

    body {
        font-family: 'Roboto', sans-serif;
    }

    .quiz-container {
        padding: 5px;
        max-width: 800px;
        margin: 0 auto;
        text-align: center;
        background-color: mediumaquamarine;
        border-radius: 10px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        color: white;
    }

    /* Style for question containers */
    .question {
        margin: 20px 0;
        padding: 15px;
        background-color: darkslategrey;
        border-radius: 5px;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
        color: white;
    }

    /* Style for radio button labels */
    .question label {
        display: block;
        margin-bottom: 5px;
    }



</style>
<html>
<head>
    <title>Start Quiz - <%= currentQuiz.getQuizName() %></title>
</head>
<body>

<jsp:include page="navbar.jsp"></jsp:include>


<div class="quiz-container">
    <h1>Welcome to <%= currentQuiz.getQuizName() %></h1>

    <form action="startQuiz" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <input type="hidden" name="start_time" id="start_time">
        <input type="submit" value="Submit">

        <% for (Question question : questions) { %>
        <div class="question">
            <h3><%= question.getQuestion() %></h3>

            <% if (question instanceof MultipleChoice) { %>
            <% MultipleChoice multipleChoice = (MultipleChoice) question;
                for (int i = 0; i < multipleChoice.getPossibleAnswers().size(); i++) { %>
            <input type="radio" name="<%= question.getId() %>" value="<%= multipleChoice.getPossibleAnswers().get(i) %>">
            <%= multipleChoice.getPossibleAnswers().get(i) %><br>
                <% } %>

            <% }  else if (question instanceof PictureResponse) { %>
            <img src="<%= ((PictureResponse) question).getImageURL() %>" alt="Question Image"><br>
            <input type="text" name="<%= question.getId() %>">


            <% } else if (question instanceof QuestionResponse) { %>
            <input type="text" name="<%= question.getId() %>">
            <% } %>

        </div>
        <% } %>

        <input type="submit" value="Submit Quiz">
    </form>
</div>

<script>
    document.getElementById("start_time").value = new Date().getTime();
</script>

</body>

</html>
