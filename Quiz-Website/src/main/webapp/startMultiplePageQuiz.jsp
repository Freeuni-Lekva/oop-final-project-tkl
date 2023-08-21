<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
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
    String quizIdString = request.getParameter("quiz_id");
    if (quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);
    Quiz currentQuiz = quizSQL.getQuizById(quizId);

    List<Question> questions = (List<Question>) request.getSession().getAttribute("CurrentQuizQuestions");
    Integer indexObj = (Integer) request.getSession().getAttribute("CurrentQuizQuestionsIndex");
    int index = indexObj != null ? indexObj : 0;
    Question currentQuestion = questions.get(index);
%>

<style>

    .question{
        text-align: center;
        padding: 15px;
        border: 2px solid mediumaquamarine;
        border-radius: 50px;
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

    .question-container{
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .question button{
        margin-top: 15px;
        position: relative;
        top: 4px;
    }
</style>

<html>
<head>
    <title>Start Quiz - <%= currentQuiz.getQuizName() %></title>
</head>
<body>

<jsp:include page="navbar.jsp"></jsp:include>

<div class="question-container">
    <h1>Welcome to <%= currentQuiz.getQuizName() %></h1>

    <form action="startMultiplePageQuiz" method="post">
        <div class="question">
            <h3><%=currentQuestion.getQuestion()%></h3>
            <input type="hidden" name="quiz_id" value="<%= quizId %>">
            <%
                out.print(currentQuestion.getHTMLCode());
            %>
            <button>Answer Question</button>
        </div>

    </form>

</div>

</body>
</html>
