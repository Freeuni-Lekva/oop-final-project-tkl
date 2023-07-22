<%@ page import="DAOinterfaces.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: 99559
  Date: 21/07/2023
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% long quizId = Long.parseLong(request.getParameter("quiz_id")); %>

<html>
<head>
    <title>Send Challenge</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

        * {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', sans-serif;
            font-weight: 500;
            font-size: 15px;
            color: white;
            text-decoration: none;
            background-color: darkslategrey;
        }

        h1 {
            text-align: center;
            margin-top: 50px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }

        label, input[type="text"] {
            margin-bottom: 20px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            border-radius: 5px;
            border: none;
            background-color: #4CAF50;
            color: white;
            cursor:pointer;
        }

        input[type="submit"]:hover {
            background-color: #3e8e41;
        }

        p {
            text-align:center;
            margin-top:20px;
        }

        a {
            font-family: 'Roboto', sans-serif;
            font-weight: 500;
            font-size: 20px;
            color: white;
            text-decoration: none;
        }

    </style>
</head>
<body>

<h1>Send Challenge</h1>
<%
    if(request.getAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME) != null){
        int message = Integer.parseInt(request.getAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME).toString());
        
        if(message == UserDao.ACCOUNT_NOT_FOUND){
            out.println("<p> Account With That Name Could Not Be Found, Try Again</p>");
        }else if(message == UserDao.SERVER_ERROR){
            out.println("<p> Encountered Some Kind Of Error. Try Again Later :(</p> ");
        }else if(message == UserDao.ACCOUNT_FOUND_BY_NAME){
            out.println("<p> Challenge Was Sent Successfully :)");
        }
    }
%>
<form action="sendChallenge" method="post">
    <input type="hidden" name="quiz_id" value="<%= quizId %>">
    <label for="receiverUsername">Receiver Username:</label>
    <input type="text" id="receiverUsername" name="receiverUsername" required><br>

    <input type="submit" value="Send Challenge">
    <br>
    <br>
    <a href="quiz.jsp?quiz_id=<%=quizId%>">Go Back</a>

</form>
</body>
</html>
