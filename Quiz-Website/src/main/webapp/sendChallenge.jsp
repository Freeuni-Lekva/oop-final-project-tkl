<%--
  Created by IntelliJ IDEA.
  User: 99559
  Date: 21/07/2023
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Challenge</title>
</head>
<body>
<h1>Send Challenge</h1>
<form action="sendChallenge" method="post">
    <label for="receiverUsername">Receiver Username:</label>
    <input type="text" id="receiverUsername" name="receiverUsername" required><br>

    <label for="quizId">Quiz ID:</label>
    <input type="text" id="quizId" name="quizId" required><br>

    <input type="submit" value="Send Challenge">
</form>
</body>
</html>
