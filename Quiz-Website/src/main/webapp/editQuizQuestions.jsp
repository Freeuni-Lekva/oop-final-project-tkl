<%@ page import="DAOinterfaces.QuestionsDao" %>
<%@ page import="java.util.List" %>
<%@ page import="Objects.Questions.Question" %>
<%@ page import="Objects.Questions.PictureResponse" %>
<%@ page import="Objects.Questions.MultipleChoice" %>
<%@ page import="Objects.Questions.QuestionResponse" %>
<%@ page import="DAOinterfaces.QuizDao" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/28/2023
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuestionsDao questionsSQL = (QuestionsDao) request.getServletContext().getAttribute(QuestionsDao.ATTRIBUTE_NAME);
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
    String quizId = request.getParameter("quiz_id");
%>

<style>

    .publishing-options{
        display: flex;
        justify-content: center;
    }
    .questions-container{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .question-box {
        padding: 5px;
        margin: 5px;
        width: 20%;
        border: 2px solid mediumaquamarine;
        border-radius: 50px;
        outline-offset: 10px;
        display: flex;
        justify-content: center;
    }

    .inner-question-box{
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .green-text{
        color: green;
    }
</style>


<html>
<head>
    <title>Quiz Questions</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <% if(quizId != null) {
        List<Question> questions = questionsSQL.getQuizQuestionsForQuiz(Long.parseLong(quizId)); %>

        <div class="publishing-options">
            <% if(quizSQL.getQuizById(Long.parseLong(quizId)).isDraft()) { %>
                <button>Publish</button>
            <% } %>
        </div>


        <div class="questions-container">
            <% for(Question question:questions) { %>
                <div class="question-box">
                    <div class="inner-question-box">
                    <p><%=question.getQuestion()%></p>
                        <p class="green-text">
                            <% if(question instanceof PictureResponse) {
                                    out.print("Picture Response");
                                }else if(question instanceof MultipleChoice){
                                    out.print("Multiple Choice");
                                }else if(question instanceof QuestionResponse){
                                    out.print("Question Response");
                                }
                            %>
                        </p>
                        <button>Delete</button>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>

</body>
</html>
