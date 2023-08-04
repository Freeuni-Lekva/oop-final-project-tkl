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
  <%
      String challengesIconURL = "images/ChallengeIcon.png";
      String friendRequestsIconURL = "images/FriendRequestIcon.png";
      String logoutIconURL = "images/logout.png";
  %>

  <style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

    body {
      color: white;
      padding: 0;
      margin: 0;
      box-sizing: border-box;
      background-color: darkslategrey;
      font-family: 'Roboto', sans-serif;
    }

    li, a, button {
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
      padding-top: 17px;
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .search-form input[type="text"] {
      padding: 9px;
      border-radius: 50px;
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
        background-color: mediumaquamarine;
        padding: 2px;
        display: none;
    }

    .popover__content {
        color: white;
        border-radius: 5px;
        max-width: 300px;
    }

    /* Style for the link */
    a.friend-requests-link {
        padding: 10px 20px;
        background-color: #4CAF50;
        border: none;
        color: white;
        cursor: pointer;
        border-radius: 5px;
        font-size: 16px;
        transition: background-color 0.3s;
        text-decoration: none;
        display: inline-block;
    }

    /* Hover effect: Change background color when hovering over the link */
    a.friend-requests-link:hover {
        background-color: #45a049;
        box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
    }

    .icon {
        width: 40px;
        height: 40px;
        cursor: pointer;
        transition: all 0.3s ease 0s;
        overflow: hidden;
        border-radius: 50%;
        border: 2px solid black;
    }

    .challenges-icon {
        /* Adjust the path to the challenges icon */
        background-image: url('<%= challengesIconURL %>');
        background-size: cover; /* You can change this to 'contain' if desired */
        /* Optionally, you can add some padding to create space around the icon */
    }

    .friend-requests-icon {
        /* Adjust the path to the friend requests icon */
        background-image: url('<%= friendRequestsIconURL %>');
    }

    .logout-icon {
        /* Adjust the path to the logout icon */
        background-image: url('<%= logoutIconURL %>');
        background-size: cover; /* You can change this to 'contain' if desired */

        border: none;
        border-radius: 0;
    }

  </style>
</head>
<body>
<header>
    <a href="index.jsp"><img class="logo" src="images/logo.png" alt="logo"></a>

  <nav>
    <ul class="navbar_options">
      <li><a href = "quizzes.jsp">Quizzes</a></li>
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
      if(id != null){
          UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);
          ChallengeDao challengeDao = (ChallengeDao) request.getServletContext().getAttribute(ChallengeDao.ATTRIBUTE_NAME);
          List<Challenge> receivedChallenges = challengeDao.getChallengesForUser(Long.parseLong(id));
          User user = userDao.getUserById(Long.parseLong(id));
          if(user == null) return; %>

          <form class="search-form" action="search" method="POST">
              <input type="text" name="searchUser" placeholder="Search...">
              <button>Search</button>
          </form>

          <div class="buttons">
              <div class="icon challenges-icon" onclick="toggleNotifications()"></div>
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

            <!-- This script fetches the updated number of friend requests from the server -->
            <!-- and displays it as a tooltip on mouseover -->
           <p id="friendRequestsLink">
               <a class="friend-requests-link" href="/friend_request?id=<%= user.getId() %>">Friend Requests</a>
           </p>

           <!-- JavaScript to show tooltip on mouseover and handle click event -->
           <script>
               const friendRequestsLink = document.getElementById("friendRequestsLink");
                // Function to fetch the friend requests count from the server
               function fetchFriendRequestsCount() {
                   fetch('/friendRequestsCount') // Replace with the actual endpoint URL
                       .then(response => response.json())
                       .then(data => {
                           const numFriendRequests = data.count;
                           friendRequestsLink.setAttribute("title", "You have " + numFriendRequests + " friend requests");
                       })
                       .catch(error => console.error('Error fetching friend requests count:', error));
               }
               // Fetch the friend requests count initially when the page loads
               fetchFriendRequestsCount();

               // Fetch the friend requests count every 100 mille seconds (adjust the interval as needed)
               setInterval(fetchFriendRequestsCount, 100);

               // Optionally, if you want to hide the tooltip on mouseout:
               friendRequestsLink.addEventListener("mouseout", () => {
                   friendRequestsLink.removeAttribute("title");
               });
           </script>

    <div class="circle-image">
              <% String profileURL = "/profile?id=" + id; %>
              <% String photoPath = "\"profile-button.jpg\""; %>
              <% photoPath = "/images/" + user.getImagePath(); %>
              <a href="<%=profileURL%>">
                  <img src="<%=photoPath%>" alt="Go To Profile">
                  <span>Go To Profile</span>
              </a>
    </div>

    <div class="buttons">
        <a href = "/logout">
            <div class="icon logout-icon"></div>
        </a>
    </div>
    <% } %>

</header>
</body>
</html>
