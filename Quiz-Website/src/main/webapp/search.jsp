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
  <title>Friends</title>
  <style>

    .user-friend-container{
      display: flex;
      flex-wrap: wrap;
      flex-direction: column;
      align-items: center;
      width: 100px;
      margin-left: 50px;
      margin-right: 50px;

    }

    .user-friends-container{
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
    }

  </style>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="user-friends-container">
  <%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("searchedUsers");
    if (users != null){
      for (int i = 0; i < users.size(); i ++){
        User user = users.get(i);
  %>
  <div class="user-friend-container">
    <div class="circle-image">
      <a href="/profile?id=<%= user.getId() %>">
        <img src="/images/<%= user.getImagePath() %>" alt="Go to Profile">
      </a>
    </div>
    <p><%= user.getName() %></p>
  </div>

  <%
      }
    }
  %>
</div>
</body>
</html>
