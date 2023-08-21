<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Questions.Question" %>
<%@ page import="Objects.Questions.MultipleChoice" %>
<%@ page import="DAOinterfaces.QuestionsDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    QuestionsDao questionsDao = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);

    Quiz quiz = quizDao.getQuizById(quizId);
    List<Question> questions = questionsDao.getQuizQuestions(quizId);
%>

<html>
<head>
    <title>Correct Answers - <%= quiz.getQuizName() %></title>
</head>
<body>

<h1>Correct Answers - <%= quiz.getQuizName() %></h1>

<% for (int i = 0; i < questions.size(); i++) {
    Question question = questions.get(i);
    List<String> correctAnswers = question.getCorrectAnswers();
%>
<div>
    <h3>Question: <%= question.getQuestion() %></h3>

    <p><%= correctAnswers.size() == 1 ? "Correct Answer:" : "Correct Answers:" %></p>
    <ul>
        <% for (String answer : correctAnswers) { %>
        <li><%= answer %></li>
        <% } %>
    </ul>
</div>
<% } %>

</body>
</html>
