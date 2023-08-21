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
    List<Question> questions = questionsSQL.getQuizQuestions(quizId, quizSQL.getQuizById(quizId).isQuestionsSorted());
%>

<style>

    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

    .quiz-container {
        padding: 5px;
        max-width: 800px;
        margin: 0 auto;
        text-align: center;
    }

    /* Style for question containers */
    .question {
        margin: 20px 0;
        padding: 15px;
        background-color: darkslategrey;
        border: 2px solid mediumaquamarine;
        border-radius: 50px;
        outline-offset: 10px;
    }

    .question input{
        border: 2px solid aquamarine;
        border-radius: 50px;
        padding: 9px;
    }

    .question img{
        height: 300px;
        width: 300px;
        border-radius: 50px;
    }

    .question input[type="radio"]{
        -webkit-appearance: none;
        width: 10px;
        height: 10px;
        border: 2px solid aquamarine;
        border-radius: 50%;
        outline: none;
        cursor: pointer;
        position: relative;
        top: 6px;
    }

    .question input[type="radio"]:checked{
        background-color: aquamarine;
        border-color: aquamarine;
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

        <% for (Question question : questions) { %>
        <div class="question">
            <h3><%= question.getQuestion() %></h3>

            <% if (question instanceof MultipleChoice) { %>
            <% MultipleChoice multipleChoice = (MultipleChoice) question;
                for (int i = 0; i < multipleChoice.getPossibleAnswers().size(); i++) { %>
            <input type="radio" name="<%= question.getId() %>" value="<%= multipleChoice.getPossibleAnswers().get(i) %>" required>
            <%= multipleChoice.getPossibleAnswers().get(i) %><br>
                <% } %>

            <% }  else if (question instanceof PictureResponse) { %>
            <img src="/images/<%= question.getImageURL() %>" alt="Question Image"><br><br>
            <input type="text" name="<%= question.getId() %>" required>


            <% } else if (question instanceof QuestionResponse) { %>
            <input type="text" name="<%= question.getId() %>" required>
            <% } %>

        </div>
        <% } %>

        <button>Finish</button>
    </form>
</div>

<script>
    document.getElementById("start_time").value = new Date().getTime();

</script>
</body>
</html>
