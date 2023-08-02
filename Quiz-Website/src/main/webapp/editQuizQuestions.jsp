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

<script>

    function clearAnswers(){

        // Getting and clearing all additional <input> labels from multipleChoiceInput and answerInput <div>
        const multipleChoiceInput = document.getElementById("multipleChoiceInput");
        const answerInput = document.getElementById("answerInput");

        while (multipleChoiceInput.firstChild){
            multipleChoiceInput.removeChild(multipleChoiceInput.firstChild);
        }

        while (answerInput.firstChild){
            answerInput.removeChild(answerInput.firstChild);
        }
    }

    function showInputs(){

        // Getting every element form "HTML" code
        const type = document.getElementById("questionType").value;
        const imageURL = document.getElementById("imageInput");
        const answerInput = document.getElementById("answerInput");
        const multipleChoiceInput = document.getElementById("multipleChoiceInput");

        // Clearing all addition answers for case when user changes type of question
        clearAnswers();

        // Changing visibility of elements according to the type value
        if(type === "question-response"){
            answerInput.style.display = "block";
            multipleChoiceInput.style.display = "none";
            imageURL.style.display = "none";
        }else if (type === "picture-response"){
            imageURL.style.display = "block";
            answerInput.style.display = "block";
            multipleChoiceInput.style.display = "none";
        }else if (type === "multiple-choice"){
            multipleChoiceInput.style.display = "block";
            answerInput.style.display = "none";
            imageURL.style.display = "none";
        }

    }

    function addCorrectAnswer(){
        const type = document.getElementById("questionType").value;
        const answerInput = document.getElementById("answerInput");
        const multipleChoiceInput = document.getElementById("multipleChoiceInput");

        const newDiv = document.createElement("div");

        const newInput = document.createElement("input");
        newInput.type = "text";
        newInput.name = "answer";
        newInput.placeholder = "Enter The Correct Answer";
        newDiv.appendChild(newInput);

        if(type === "multiple-choice"){
            const checkBox = document.createElement("input");
            checkBox.type = "checkbox";
            checkBox.name = "correctAnswer";
            newDiv.appendChild(checkBox);
            multipleChoiceInput.appendChild(newDiv);
            return;
        }

        answerInput.appendChild(newDiv);
    }
</script>


<html>
<head>
    <title>Quiz Questions</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <% if(quizId != null) {
        List<Question> questions = questionsSQL.getQuizQuestionsForQuiz(Long.parseLong(quizId)); %>

        <div class="new-questions-container">
            <div class="nqc-type">
                <label>Select Question Type</label>
                <select id="questionType" name="questionType" onchange="showInputs()">
                    <option value="picture-response">Picture-Response</option>
                    <option value="question-response">Question-Response</option>
                    <option value="multiple-choice">Multiple-Choice</option>
                </select>
            </div>

            <div id="questionInput">
                <input type="text" name="question" placeholder="Enter The Question..">
            </div>

            <div id="imageInput">
                <input type="text" name="imageURL" placeholder="Enter The Image URL..">
            </div>

            <div id="answerInput">
                <input type="text" name="answer" placeholder="Enter The Correct Answer">
            </div>

            <div id="multipleChoiceInput" style="display: none"></div>

            <button type="button" onclick="addCorrectAnswer()">+</button>
            <button type="submit">Add Question</button>
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
