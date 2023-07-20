<%@ page import="DAOinterfaces.UserDao" %>
<%@ page import="Objects.User" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 6/20/2023
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="website-icon" type="x-icon" href="images/login.png">
  <title>TKL-Quiz-Website</title>

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

    header{
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px 10%;
    }

    .logo{
      cursor: pointer;
    }

    .navbar_options li{
      display: inline-block;
      padding: 0px 20px;
    }

    .navbar_options li a{
      transition: all 0.3s ease 0s;
    }

    .navbar_options li a:hover{
      color: aquamarine;
    }

    button{
      padding: 5px 20px;
      background-color: mediumaquamarine;
      border: none;
      border-radius: 50px;
      cursor: pointer;
      transition: all 0.3s ease 0s;
    }

    button:hover{
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

  </style>

</head>
<body>

<header>
  <img class="logo" src="images/logo.png" alt="logo">

  <nav>
    <ul class="navbar_options">
      <li><a href = "quizes.jsp">Quizes</a></li>
      <li><a href = "test.jsp">Home</a></li>
    </ul>
  </nav>
  <%
    String id = (String) request.getSession().getAttribute("main_user_id");
    if(id == null){
  %>
  <div class="buttons">
    <a href="login.jsp"><button>Login</button></a>
    <a href="signUp.jsp"><button>Sign Up</button></a>
  </div>
  <%
    }
    if(id != null){
  %>
  <form class="search-form" action="search" method="POST">
    <input type="text" name="searchUser" placeholder="Search...">
    <input type="submit" value="Search">
  </form>
  <%
      String photoPath = "\"profile-button.jpg\"";
      UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
      User user = userDao.getUserById(Long.parseLong(id));

      out.println("<div class=\"circle-image\">");
      String profileURL = "/profile?id=" + id;
      out.println("<a href=\"" + profileURL + "\">");
      if (user != null) photoPath = "/images/" + user.getImagePath();
      out.println("<img src=" + photoPath + " alt=\"Go to Profile\">");
      out.println("<span>Go to profile</span>");
      out.println("</a>");
      out.println("</div>");
    }
  %>
</header>
</body>
</html>
