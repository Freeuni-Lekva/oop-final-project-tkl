<%@ page import="DAOinterfaces.UserDao" %>
<%@ page import="DAOinterfaces.FriendRequestDao" %>
<%@ page import="Objects.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>
    body {
      background-color: darkslategrey;
      color: white;
      font-family: 'Roboto', sans-serif;
      font-weight: 500;
      font-size: 20px;
    }

    .container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 20px;
      padding: 20px;
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

    .friend-request-form {
      display: flex;
      gap: 10px;
      align-items: center;
    }

    .friend-request-form button {
      background-color: #4caf50;
      color: white;
      border: none;
      padding: 10px 20px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      font-size: 14px;
      cursor: pointer;
    }

    .friend-request-form button.reject {
      background-color: #f44336;
    }
  </style>
</head>
<body>
<%
  UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
  FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);
  List<User> friend_requests = (List<User>) request.getSession().getAttribute("friend_requests");
%>
<div class="container">
  <%
    if (friend_requests != null) {
      for (int i = 0; i < friend_requests.size(); i++) {
        User user = friend_requests.get(i);
        String profileURL = "/profile?id=" + user.getId();
        String photoPath = "/images/" + user.getImagePath();
  %>
  <div class="circle-image">
    <a href="<%= profileURL %>">
      <img src="<%= photoPath %>" alt="Go to Profile">
    </a>
  </div>
  <form class="friend-request-form" action="/friend_request" method="post">
    <input type="hidden" name="friendID" value="<%= user.getId() %>">
    <button type="submit" name="accept" value="true">Accept</button>
    <button class="reject" type="submit" name="accept" value="false">Reject</button>
  </form>
  <%
      }
    }
  %>
</div>
</body>
</html>
