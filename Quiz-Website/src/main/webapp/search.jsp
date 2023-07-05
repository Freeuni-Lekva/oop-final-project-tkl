<%@ page import="Objects.User" %>
<%@ page import="DAOinterfaces.UserDao" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21.06.2023
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>
    .circle-image {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      overflow: hidden;
    }

    .circle-image img {
      object-fit: cover;
      width: 100%;
      height: 100%;
    }

    body {
      background-color: darkslategrey;
      color: white;
      font-family: 'Roboto', sans-serif;
      font-weight: 500;
      font-size: 20px;
    }

    h1 {
      color: #333;
    }

    .profile-container {
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
    }

    .profile-container p {
      margin-bottom: 10px;
    }

    .edit-button {
      display: inline-block;
      padding: 10px 20px;
      background-color: mediumaquamarine;
      color: white;
      text-decoration: none;
      border: none;
      border-radius: 5px;
      font-weight: bold;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .edit-button:hover {
      background-color: aquamarine;
    }

    .profile-container a {
      color: aquamarine;
      text-decoration: none;
      font-weight: bold;
    }

    .profile-container a:hover {
      color: white;
    }
  </style>
</head>
<body>
<form action="search" method="POST">
  <input type="text" name="searchUser" placeholder="Search...">
  <input type="submit" value="Search">
</form>
<%
  ArrayList<User> users = (ArrayList<User>) request.getAttribute("searchedUsers");
  if (users != null){
    for (int i = 0; i < users.size(); i ++){
      User user = users.get(i);
%>
<div class="circle-image">
  <a href="/profile?id=<%= user.getId() %>">
    <img src="/images/<%= user.getImagePath() %>" alt="Go to Profile">
  </a>
</div>
<form action="search" method="post">
  <p>Username: <%= user.getName() %></p>
</form>
<%
    }
  }
%>
</body>
</html>
