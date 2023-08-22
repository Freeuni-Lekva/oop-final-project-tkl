<%@ page import="java.util.List" %>
<%@ page import="Objects.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.06.2023
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

    .username {
      font-size: 18px;
    }

    .remove-button {
      background-color: indianred;
    }

    .remove-button:hover {
      background-color: firebrick;
    }

    .center-container input[type="text"] {
      border: 2px solid #ccc;
      border-radius: 20px;
      padding: 10px;
      margin: 10px;
      font-size: 16px;
    }

    .center-container input[type="text"]:focus {
      border-color: aquamarine;
      outline: none;
    }

    .friend-container {
      padding: 5px;
      margin: 5px;
      border: 2px solid mediumaquamarine;
      border-radius: 20px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 20px;
    }

    .friend-info {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .friend-info form{
      display: flex;
      align-items: center;
    }

    .center-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
    }
  </style>

</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="center-container">
  <%
    List<User> friends = (List<User>) request.getSession().getAttribute("userFriends");
    String main_user_id = (String) request.getSession().getAttribute("MainUserID");
    String profile = (String) request.getSession().getAttribute("profileUser");

    if (friends != null){
      for (int i = 0; i < friends.size(); i++){
        User user = friends.get(i);
  %>

  <div class="friend-container">
    <div class="circle-image">
      <a href="/profile?id=<%= user.getId() %>">
        <img src="/images/<%= user.getImagePath() %>" alt="Go to Profile">
      </a>
    </div>
    <p class="username">Username: <%= user.getName() %></p>
  <%
    if(main_user_id.equals(profile)){
  %>
    <div class="friend-info">
      <form action="/send_note" method="POST">
        <input type="hidden" name="friend_id" value="<%= user.getId() %>">
        <input type="text" name="noteText" placeholder="Enter your note">
        <button type="submit">Send Note</button>
      </form>

      <form action="/remove_friend" method="POST">
        <input type="hidden" name="friend_id" value="<%= user.getId() %>">
        <button class="remove-button" type="submit">Remove Friend</button>
      </form>
    </div>
  </div>

  <%
        }
      }
    }
  %>
</div>
</body>
</html>
