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

<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

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

    .new-questions-container{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .new-questions-container form{
        padding: 20px;
        width: 100%;
        border: 2px solid mediumaquamarine;
        border-radius: 50px;
        outline-offset: 10px;
        align-items: center;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .new-question{
        display: flex;
        flex-direction: column;
        justify-content: center;
        padding: 5px;
    }

    
</style>

<script>

    function clearAnswers(){

        // Getting and clearing all additional <input> labels from multipleChoiceInput and answerInput <div>
        const multipleChoiceInput = document.getElementById("multipleChoiceInput");
        const answerInput = document.getElementById("answerInput");

        // Clearing all <input> labels for multiple-choice questions
        while (multipleChoiceInput.firstChild){
            multipleChoiceInput.removeChild(multipleChoiceInput.firstChild);
        }

        // Clearing all <input> labels for other type of questions
        while (answerInput.firstChild){
            answerInput.removeChild(answerInput.firstChild);
        }

    }

    function showInputs(){

        // Getting every element form "HTML" code
        const type = document.getElementById("questionType").value;
        const question = document.getElementById("questionInput");
        const imageURL = document.getElementById("imageInput");
        const answerInput = document.getElementById("answerInput");
        const multipleChoiceInput = document.getElementById("multipleChoiceInput");
        const imageLabelDiv = document.getElementById("imageInputLabel");
        const answersLabelDiv = document.getElementById("answersLabel");
        const questionLabelDiv = document.getElementById("questionLabel");

        // Clearing all addition answers for case when user changes type of question
        clearAnswers();

        // Changing visibility of elements according to the type value
        if(type === "question-response"){
            questionLabelDiv.style.display = "block";
            answersLabelDiv.style.display = "block";
            question.style.display = "block";
            answerInput.style.display = "block";
            multipleChoiceInput.style.display = "none";
            imageURL.style.display = "none";
            imageLabelDiv.style.display = "none";
        }else if (type === "picture-response"){
            questionLabelDiv.style.display = "block";
            answersLabelDiv.style.display = "block";
            question.style.display = "block";
            imageURL.style.display = "block";
            answerInput.style.display = "block";
            imageLabelDiv.style.display = "block";
            multipleChoiceInput.style.display = "none";
        }else if (type === "multiple-choice"){
            questionLabelDiv.style.display = "block";
            answersLabelDiv.style.display = "block";
            question.style.display = "block";
            multipleChoiceInput.style.display = "block";
            imageLabelDiv.style.display = "none";
            answerInput.style.display = "none";
            imageURL.style.display = "none";
        }else{
            questionLabelDiv.style.display = "none";
            answersLabelDiv.style.display = "none";
            question.style.display = "none";
            multipleChoiceInput.style.display = "none";
            imageLabelDiv.style.display = "none";
            answerInput.style.display = "none";
            imageURL.style.display = "none";
        }

    }

    function countCheckboxes(){

        const multipleChoiceDiv = document.getElementById("multipleChoiceInput");
        const checkBoxes = multipleChoiceDiv.querySelectorAll('input[type="checkbox"]');
        return "" + checkBoxes.length;
    }

    function addCorrectAnswer(){

        // Getting elements from "HTML"
        const type = document.getElementById("questionType").value;
        const answerInput = document.getElementById("answerInput");
        const multipleChoiceInput = document.getElementById("multipleChoiceInput");

        if(type === "empty") {
            alert("Choose Question Type Firstly Before Add Correct Answers");
            return;
        }

        // Creating new div
        const newDiv = document.createElement("div");

        // Creating new input element and add it to the div
        const newInput = document.createElement("input");
        newInput.type = "text";
        newInput.name = "answer";
        newInput.required = true;
        newInput.placeholder = "Enter The Correct Answer";
        newDiv.appendChild(newInput);

        // If users chooses multiple-choice question and presses on + button code adds input label with checkbox
        // to determine which answer is correct
        if(type === "multiple-choice"){
            const checkBox = document.createElement("input");
            checkBox.type = "checkbox";
            checkBox.name = "correctAnswer";
            checkBox.value = countCheckboxes();
            newDiv.appendChild(checkBox);
            multipleChoiceInput.appendChild(newDiv);
            return;
        }

        answerInput.appendChild(newDiv);
    }

    function validateInput(){

        // Getting type of question from <select>
        const type = document.getElementById("questionType").value;

        if(type === "empty"){
            alert("Choose Question Type Before Add Question To Quiz");
            return false;

        }else if(type === "multiple-choice"){

            // Getting text and checkbox inputs
            const multipleChoiceDiv = document.getElementById("multipleChoiceInput");
            const answerInputs = multipleChoiceDiv.querySelectorAll('input[type="text"]');
            const possibleAnswers = answerInputs.length;

            // If user adds only one possible answer for multiple-choice question function returns false
            if(possibleAnswers < 2){
                alert("Add At Least 2 Possible Questions");
                return false;
            }

            const checkBoxes = multipleChoiceDiv.querySelectorAll('input[type="checkbox"]');

            console.log(checkBoxes.length)

            let counter = 0;
            checkBoxes.forEach(checkbox =>{
                if(checkbox.checked){
                    counter++;
                }
            });

            // If user doesn't choose at least one checkbox function returns false
            if(counter < 1){
                alert("Choose At Least One Correct Answer");
                return false;
            }
        }else{

            const simpleAnswersDiv = document.getElementById("answerInput");
            const answerInputs = simpleAnswersDiv.querySelectorAll('input[type="text"]');
            const answers = answerInputs.length;

            // If user doesn't add at least one correct answer function returns false
            if(answers < 1){
                alert("Add At Leas One Correct Answer");
                return false;
            }
        }

        return true;
    }
</script>


<html>
<head>
    <title>Quiz Questions</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <% if(quizId != null) {
        List<Question> questions = questionsSQL.getQuizQuestions(Long.parseLong(quizId)); %>

        <div class="new-questions-container">
            <form action="CreateNewQuestionsServlet" method="post" onsubmit="return validateInput()">
                <input type="hidden" name="quiz_id" value="<%=quizId%>">
                <div class="nqc-type">
                    <label>Select Question Type</label>
                    <select id="questionType" name="questionType" onchange="showInputs()">
                        <option value="empty">Select Type</option>
                        <option value="picture-response">Picture-Response</option>
                        <option value="question-response">Question-Response</option>
                        <option value="multiple-choice">Multiple-Choice</option>
                    </select>
                </div>

                <div id="questionLabel" class="new-question" style="display: none;">
                    <label>Question</label>
                </div>
                <div id="questionInput" class="new-question" style="display: none;">
                    <input type="text" name="question" placeholder="Enter The Question.." required>
                </div>

                <div id="imageInputLabel" class="new-question" style="display: none;">
                    <label>Image</label>
                </div>

                <div id="imageInput" class="new-question" style="display: none;">
                    <input type="text" name="imageURL" placeholder="Enter The Image URL..">
                </div>

                <div id="answersLabel" class="new-questions" style="display: none;">
                    <label>Add Answers</label>
                </div>

                <div id="answerInput" class="new-question" style="display: none;"></div>

                <div id="multipleChoiceInput" class="new-question" style="display: none;"></div>

                <br>
                <button type="button" onclick="addCorrectAnswer()">+</button>
                <br>
                <button type="submit">Add Question</button>
            </form>
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
