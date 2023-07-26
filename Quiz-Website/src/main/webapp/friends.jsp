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

    * {
      padding: 0;
      margin: 0;
      box-sizing: border-box;
      background-color: darkslategrey;
    }

    li, a, button {
      font-family: 'Roboto', sans-serif;
      font-weight: 500;
      font-size: 20px;
      color: white;
      text-decoration: none;
    }

    header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px 10%;
    }

    .logo {
      cursor: pointer;
    }

    .navbar_options li {
      display: inline-block;
      padding: 0px 20px;
    }

    .navbar_options li a {
      transition: all 0.3s ease 0s;
    }

    .navbar_options li a:hover {
      color: aquamarine;
    }

    .buttons {
      display: flex;
      align-items: center;
      gap: 10px;
    }

    button {
      padding: 5px 20px;
      background-color: mediumaquamarine;
      border: none;
      border-radius: 50px;
      cursor: pointer;
      transition: all 0.3s ease 0s;
    }

    button:hover {
      background-color: aquamarine;
    }

    .search-form {
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .search-form input[type="text"] {
      padding: 5px;
      border-radius: 5px;
      border: none;
      outline: none;
    }

    .search-form input[type="submit"] {
      padding: 5px 20px;
      background-color: aquamarine;
      border: none;
      border-radius: 50px;
      cursor: pointer;
      transition: all 0.3s ease 0s;
      color: white;
      font-weight: bold;
    }

    .search-form input[type="submit"]:hover {
      background-color: mediumaquamarine;
    }

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

    .username {
      color: white;
      font-size: 18px;
      margin-top: 10px;
    }

    .remove-button {
      padding: 5px 20px;
      background-color: indianred;
      border: none;
      border-radius: 50px;
      cursor: pointer;
      transition: all 0.3s ease 0s;
      color: white;
      font-weight: bold;
    }

    .remove-button:hover {
      background-color: firebrick;
    }
  </style>

</head>
<body>
<%
  List<User> friends = (List<User>) request.getSession().getAttribute("user_friends");
  String main_user_id = (String) request.getSession().getAttribute("MainUserID");
  String profile = (String) request.getSession().getAttribute("profileUser");

  if (friends != null){
    for (int i = 0; i < friends.size(); i++){
      User user = friends.get(i);
%>
<div class="circle-image">
  <a href="/profile?id=<%= user.getId() %>">
    <img src="/images/<%= user.getImagePath() %>" alt="Go to Profile">
  </a>
</div>
<p class="username">Username: <%= user.getName() %></p>
<%
  if(main_user_id.equals(profile)){
%>
<form action="/remove_friend" method="POST">
  <input type="hidden" name="friend_id" value="<%= user.getId() %>">
  <button class="remove-button" type="submit">Remove Friend</button>
</form>
<%
      }
    }
  }
%>
</body>
</html>
