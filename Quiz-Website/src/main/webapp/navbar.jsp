<%@ page import="DAOinterfaces.UserDao" %>
<%@ page import="Objects.User" %>
<%@ page import="DAOinterfaces.ChallengeDao" %>
<%@ page import="Objects.Challenge" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 6/20/2023
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function toggleNotifications() {
        let popover = document.getElementById("notificationsPopover");
        popover.style.display = (popover.style.display === "block") ? "none" : "block";
    }
</script>
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

    .circle-image {
      width: 80px;
      height: 80px;
      border-radius: 50%;
      overflow: hidden;
    }

    .circle-image img {
      object-fit: cover;
      width: 100%;
      height: 100%;
    }

    .popover {
        position: absolute;
        top: 100px;
        right: 94px;
        background-color: white;
        padding: 2px;
        display: none;
    }

    .popover__content {
        background-color: #fff;
        /*box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);*/
        border-radius: 5px;
        max-width: 300px;
        /*color: white; */
    }

  </style>

</head>
<body>

<header>
  <img class="logo" src="images/logo.png" alt="logo">

  <nav>
    <ul class="navbar_options">
      <li><a href = "quizzes.jsp">Quizes</a></li>
      <li><a href = "createQuiz.jsp">Create</a></li>
    </ul>
  </nav>
  <%
    String id = (String) request.getSession().getAttribute("MainUserID");
    if(id == null){
  %>
  <div class="buttons">
    <a href="login.jsp"><button>Login</button></a>
    <a href="signUp.jsp"><button>Sign Up</button></a>
  </div>
  <% } %>

  <%
      UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
      ChallengeDao challengeDao = (ChallengeDao) request.getServletContext().getAttribute(ChallengeDao.ATTRIBUTE_NAME);
      List<Challenge> receivedChallenges = challengeDao.getChallengesForUser(Long.parseLong(id));
      User user = userDao.getUserById(Long.parseLong(id));

      if(id != null){ %>

          <form class="search-form" action="search" method="POST">
              <input type="text" name="searchUser" placeholder="Search...">
              <button>Search</button>
          </form>

          <div class="buttons">
              <!-- Notifications button -->
              <button onclick="toggleNotifications()">Challenges</button>

              <!-- Notifications popover -->
              <div class="popover" id="notificationsPopover">
                  <div class="popover__content">

                      <% int notifications = receivedChallenges==null?0:receivedChallenges.size();%>
                      <p>You have <%=notifications%> new challenges!</p>
                      <%if(notifications != 0) { %>

                          <% for (Challenge challenge : receivedChallenges) { %>
                          <!-- Challenge Box -->
                          <div class="challenge-box">
                              <p><%=user.getName() %><strong> has challenged you:</strong> </p>
                              <p><strong>Quiz ID:</strong><%= challenge.getQuizId()%> </p>
                              <p><strong>Timestamp:</strong> <%= challenge.getTimestamp() %></p>
                              <form action="acceptChallenge" method="post">
                                  <input type="hidden" name="challenge_id" value="<%= challenge.getId() %>">
                                  <input type="submit" value="Accept Challenge">
                              </form>
                          </div>
                          <% } %>
                      <% } %>
                  </div>
              </div>
          </div>
          <div class="circle-image">
              <% String profileURL = "/profile?id=" + id; %>
              <% String photoPath = "\"profile-button.jpg\""; %>
              <% if (user != null) photoPath = "/images/" + user.getImagePath(); %>
              <a href="<%=profileURL%>">
                  <img src="<%=photoPath%>" alt="Go To Profile">
                  <span>Go To Profile</span>
              </a>
          </div>

    <% } %>

</header>
</body>
</html>
