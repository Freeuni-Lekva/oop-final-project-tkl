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

        h1 {
            font-size: 24px;
            margin-top: 0;
        }

        .edit-profile-form {
            max-width: 400px;
            margin: 0 auto;
            background-color: darkslategrey;
            padding: 20px;
            border: 2px solid mediumaquamarine;
            border-radius: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        .edit-profile-form input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #CCC;
            border-radius: 10px;
            font-size: 16px;
            margin-bottom: 20px;
        }

    </style>
</head>
<body>

<jsp:include page="navbar.jsp"></jsp:include>

<%
    User user = (User) request.getSession().getAttribute("mainUser");
%>
    <form class="edit-profile-form" action="edit_user" method="post">
        <h1>Edit Profile</h1>
        <input type="hidden" name="id" value="<%= user.getId() %>" required>
        <label for="realName">Real Name:</label>
        <input type="text" id="realName" name="realName" value="<%= user.getRealName() %>" required>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="<%= user.getRealLastName() %>" required>
        <label for="imagePath">Image Path:</label>
        <input type="text" id="imagePath" name="imagePath" value="<%= user.getImagePath() %>" required>
        <label for="description">Description:</label>
        <textarea id="description" name="description" required><%= user.getDescription() %></textarea>
        <button>Save Changes</button>
    </form>
</body>
</html>
