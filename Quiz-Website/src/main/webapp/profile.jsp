<%@ page import="Objects.User" %>
<%@ page import="DAOinterfaces.UserDao" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21.06.2023
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>
    .image-container {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      overflow: hidden;
    }

    .image-container img {
      object-fit: cover;
      width: 100%;
      height: 100%;
    }

    body {
      color: white;
    }

    h1 {
      color: #333;
    }

    .profile-container {
      font-weight: 500;
      font-size: 20px;
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
    }

    .profile-container p {
      margin-bottom: 10px;
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
<jsp:include page="navbar.jsp"/>
<div class="profile-container">
  <%
    User user = (User) request.getSession().getAttribute("profileUser");
    String main_user_id = (String) request.getSession().getAttribute("MainUserID");
  %>
  <p><strong>Name:</strong> <%= user.getName() %></p>
  <p><strong>Real Name:</strong> <%= user.getRealName() %></p>
  <p><strong>Last Name:</strong> <%= user.getRealLastName() %></p>
  <div class="image-container">
    <img src="/images/<%= user.getImagePath() %>">
  </div>
  <p><strong>Description:</strong> <%= user.getDescription() %></p>
  <%
    if (user.getId() == Long.parseLong(main_user_id)) {
      out.println("<div class=\"edit-form-container\">");
      out.println("<form action=\"/edit_user\" method=\"GET\">");
      out.println("<input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\">");
      out.println("<button class=\"edit-button\" type=\"submit\">Edit</button>");
      out.println("</form>");
      out.println("</div>");
    }
    out.println("<p><a href=\"/friends?id=" + user.getId() + "\">See Friends</a></p>");
  %>
  <a href="history.jsp">View History</a>
  <jsp:include page="/add_friend" />
  <%

    if (Boolean.TRUE.equals(request.getSession().getAttribute("addButton?"))){
      out.println("<form action=\"/add_friend\" method=\"POST\">");
      out.println("<button type=\"submit\">Add Friend</button>");
      out.println("</form>");
    }
  %>
</div>
</body>
</html>
