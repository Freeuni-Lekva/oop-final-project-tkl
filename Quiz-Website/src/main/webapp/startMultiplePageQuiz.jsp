<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="DAOinterfaces.QuestionsDao" %>
<%@ page import="Objects.Questions.Question" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 8/21/2023
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    QuestionsDao questionsSQL = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);

    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    Quiz currentQuiz = quizSQL.getQuizById(quizId);

    List<Question> questions = questionsSQL.getQuizQuestions(quizId, quizSQL.getQuizById(quizId).isQuestionsSorted());
    String questionIndexString = request.getParameter("question_index");
    int questionIndex = Integer.parseInt(questionIndexString);
%>

<html>
<head>
    <title>Start Quiz - <%= currentQuiz.getQuizName() %></title>
</head>
<body>

<jsp:include page="navbar.jsp"></jsp:include>

<div class="quiz-container">
    <h1>Welcome to <%= currentQuiz.getQuizName() %></h1>

    <form action="startMultiplePageQuiz" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <input type="hidden" name="start_time" value="<%= request.getParameter("start_time") %>">
        <input type="hidden" name="question_index" value="<%= questionIndex + 1 %>">

        <div class="question">
            <% Question question = questions.get(questionIndex); %>
            <h3><%= question.getQuestion() %></h3>

            <%
                out.println(question.getHTMLCode());
            %>

        </div>

    </form>

</div>

</body>
</html>
