<%@ page import="DAOinterfaces.QuizDao" %>
<%@ page import="Objects.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="Objects.Questions.*" %>
<%@ page import="DAOinterfaces.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/20/2023
  Time: 8:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String quizIdString = request.getParameter("quiz_id");
    if(quizIdString == null) quizIdString = (String) request.getSession().getAttribute("quiz_id");
    long quizId = Long.parseLong(quizIdString);

    UserDao userSQL = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
    QuizDao quizSQL = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);

    Quiz currentQuiz = quizSQL.getQuizById(quizId);
%>

<style>

    .top-quiz-container{
        display: flex;
        justify-content: space-around;
        align-items: center;
    }
    .quiz-information, .quiz-buttons{
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .quiz-information h3, p{
        margin: 7px;
    }

    .challenge-container{
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .challenge form {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .message-container p {
        text-align:center;
        margin-top:10px;
    }

    .challenge input{
        border: 2px solid aquamarine;
        border-radius: 50px;
        padding: 9px;
    }

    .bottom-quiz-container{
        display: flex;
        flex-wrap: wrap;
        flex-direction: column;
        align-items: center;
    }
</style>
<html>
<head>
    <title><%=currentQuiz.getQuizName()%></title>
</head>
<body>

    <jsp:include page="navbar.jsp"></jsp:include>

    <div class="quiz-container">

        <div class="top-quiz-container">

            <div class="challenge-container">

                <div class="message-container">
                    <%
                        if(request.getAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME) != null){
                            int message = Integer.parseInt(request.getAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME).toString());

                            if(message == UserDao.ACCOUNT_NOT_FOUND){
                                out.println("<h3> Account With That Name Could Not Be Found, Try Again</h3>");
                            }else if(message == UserDao.SERVER_ERROR){
                                out.println("<h3> Encountered Some Kind Of Error. Try Again Later :(</h3> ");
                            }else if(message == UserDao.ACCOUNT_FOUND_BY_NAME){
                                out.println("<h3> Challenge Was Sent Successfully :) </h3>");
                            }
                        }
                    %>
                </div>

                <div class="challenge">
                    <form action="sendChallenge" method="post">
                        <input type="hidden" name="quiz_id" value="<%= quizId %>">
                        <label for="receiverUsername">Receiver Username:</label><br>
                        <input type="text" id="receiverUsername" name="receiverUsername" required><br>

                        <button>Send Challenge</button>
                    </form>
                </div>
            </div>

            <div class="quiz-information-container">

                <div class="quiz-information">
                    <h3><%=currentQuiz.getQuizName()%></h3>
                    <p>Creator: <%=userSQL.getUserById(currentQuiz.getCreatorId()).getName()%></p>
                    <p><%=currentQuiz.getDescription()%></p>
                </div>

                <div class="quiz-buttons">
                    <a href="startQuiz.jsp?quiz_id=<%=quizId%>&is_practice=0"><button>Start Quiz</button></a> <br>
                    <% if(currentQuiz.isPractice()) { %>
                        <a href="startQuiz.jsp?quiz_id=<%=quizId%>&is_practice=1"><button>Practice Mode</button></a>
                    <% } %>
                </div>
            </div>
        </div>

        <br><br>

        <div class="bottom-quiz-container">

            <!-- that div can be used for comments/statistics -->
            <jsp:include page="quizTopScores.jsp"></jsp:include>

        </div>
    </div>
</body>
</html>
