<%@ page import="Objects.User" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21.06.2023
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            background-color: #F5F7FA;
            color: #333;
            font-family: 'Roboto', sans-serif;
            font-weight: 500;
            font-size: 16px;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #333;
            font-size: 24px;
            margin-top: 0;
        }

        form {
            max-width: 400px;
            margin: 0 auto;
            background-color: #FFF;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #CCC;
            border-radius: 3px;
            font-size: 16px;
            margin-bottom: 20px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("MainUser");
%>
<form action="edit_user" method="post">
    <h1>Edit Profile</h1>
    <input type="hidden" name="name" value="<%= user.getName() %>">
    <label for="realName">Real Name:</label>
    <input type="text" id="realName" name="realName" value="<%= user.getRealName() %>">
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="<%= user.getRealLastName() %>">
    <label for="imagePath">Image Path:</label>
    <input type="text" id="imagePath" name="imagePath" value="<%= user.getImagePath() %>">
    <label for="description">Description:</label>
    <textarea id="description" name="description"><%= user.getDescription() %></textarea>
    <input type="submit" value="Save Changes">
</form>
</body>
</html>
