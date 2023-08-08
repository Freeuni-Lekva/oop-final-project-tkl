<%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 7/28/2023
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    .container{
        margin: auto;
        width: 500px;
        height: 380px;
        max-height: 90%;
        max-width: 90%;
    }

    .container form{
        width: 100%;
        height: 100%;
        padding: 10px;
        background: mediumaquamarine;
        border-radius: 5px;
        box-shadow: 0 8px 16px black;
    }

    .container form h1{
        text-align: center;
        margin-bottom: 20px;
        color: white;
    }

    .container form .text-field{
        width: 100%;
        height: 30px;
        background: white;
        border-radius: 4px;
        border: 1px solid silver;
        margin: 10px 0 18px 0;
        padding: 0 10px;
    }

    .container form button{
        margin-top: 50px;
        margin-left: 50%;
        transform: translateX(-50%);
        width: 200px;
        height: 60px;
        border: none;
        border-radius: 50px;
        cursor: pointer;
        transition: all 0.3s ease 0s;
        background: darkslategrey;
        color: white;
    }

    .container form button:hover{
        background-color: aquamarine;
    }

    .container label{
        color: white;
    }

    .container form i{
        color: white;
    }

    .container form p{
        text-align: center;
        margin-top: 0px;
        color: aquamarine;
    }

</style>

<html>
<head>
    <title>Create New Quiz</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    <div class="container">
        <form action="CreateNewQuizServlet" method="post">
            <div class="new-quiz-option-container">
                <i class='bx bxs-invader'></i> <label>Quiz Name: </label>
                <input type="text" placeholder="Name" class="text-field" name="quizName" required>
            </div>
            <div class="new-quiz-option-container">
                <i class='bx bx-message-square-dots'></i> <label>Quiz Description (60 Words Max): </label>
                <input type="text" placeholder="Description" class="text-field" name="description" maxlength="60" required>
            </div>
            <div class="new-quiz-option-container">
                <i class='bx bx-ghost'></i> <label>Is Practice: </label>
                <input type="checkbox" name="isPractice">
            </div>
            <button type="submit">Add Questions</button>
        </form>
    </div>

</body>
</html>
