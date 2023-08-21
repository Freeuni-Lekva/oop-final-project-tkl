<%@ page import="Objects.User" %>
<%@ page import="Objects.Challenge" %>
<%@ page import="java.util.List" %>
<%@ page import="Objects.Note" %>
<%@ page import="DAOinterfaces.*" %><%--
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

  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

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
        right: 100px;
        background-color:  darkslategrey;
        padding: 10px;
        display: none;
        margin: 20px;
        border: 2px solid aquamarine;
        border-radius: 10px;
        text-align: center;
    }

    .popover__content {
        color: white;
        max-width: 300px;
    }

    .notification-box{
        border-bottom: 2px solid aquamarine;
        border-radius: 10px;
    }

    .bx{
        color: white;
        cursor: pointer;
        border: 2px solid mediumaquamarine;
        border-radius: 50%;
        padding: 8px;
        transition: background-color 0.3s;
    }

    .bx-log-out-circle{
        padding: 6px;
    }

    .bx:hover{
        border-color: aquamarine;
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
          QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.ATTRIBUTE_NAME);
          NotesDao notesDao = (NotesDao) request.getServletContext().getAttribute(NotesDao.ATTRIBUTE_NAME);

          FriendRequestDao requestDao = (FriendRequestDao) request.getServletContext().getAttribute(FriendRequestDao.ATTRIBUTE_NAME);
          List<User> requests = requestDao.getReceivedFriendRequests(Long.parseLong(id));
          List<Challenge> receivedChallenges = challengeDao.getChallengesForUser(Long.parseLong(id));
          List<Note> notes = notesDao.getNotes(Long.parseLong(id));

          User user = userDao.getUserById(Long.parseLong(id));

          if(user == null) {
              request.getSession().setAttribute("MainUserID", null);
              return;
          } %>

          <form class="search-form" action="search" method="POST">
              <input type="text" name="searchUser" placeholder="Search...">
              <button>Search</button>
          </form>

          <div class="buttons">
              <div class="input-box">
                  <i class='bx bx-bell' onclick="toggleNotifications()"></i>
              </div>
              <!-- Notifications popover -->
              <div class="popover" id="notificationsPopover">
                  <div class="popover__content challenges">

                      <% int notifications = receivedChallenges==null?0:receivedChallenges.size();%>
                      <div class="notification-box">
                          <p>You have <%=notifications%> new challenges!</p>
                      </div>
                      <%if(notifications != 0) { %>

                          <% for (Challenge challenge : receivedChallenges) { %>
                          <!-- Challenge Box -->
                          <div class="notification-box">
                              <p><strong><%=userDao.getUserById(challenge.getSenderId()).getName()%></strong> has challenged you</p>
                              <p><strong>Quiz Name: </strong><%= quizDao.getQuizById(challenge.getId()).getQuizName()%> </p>
                              <p><strong>Timestamp: </strong> <%= challenge.getTimestamp() %></p>
                              <form action="acceptChallenge" method="post">
                                  <input type="hidden" name="challenge_id" value="<%= challenge.getId() %>">
                                  <input type="hidden" name="referringPage" value="<%= request.getRequestURI() %>">
                                  <button name="accept" value="true">Accept</button>
                                  <button name="accept" value="false">Reject</button>
                              </form>
                          </div>
                          <% } %>
                      <% } %>
                  </div>

                  <div class="popover__content friends">

                      <% notifications = requests.size(); %>
                      <div class="notification-box">
                        <p>You have <%=notifications%> new friend request</p>
                      </div>

                      <% if(notifications != 0) { %>

                            <% for(User friend: requests){ %>

                                  <div class="notification-box">
                                      <p><strong><%=friend.getName()%></strong> has sent you friend request!</p>
                                      <form action="/friend_request" method="post">
                                          <input type="hidden" name="friendID" value="<%= friend.getId() %>">
                                          <input type="hidden" name="referringPage" value="<%= request.getRequestURI() %>">
                                          <button type="submit" name="accept" value="true">Accept</button>
                                          <button class="reject" type="submit" name="accept" value="false">Reject</button>
                                      </form>
                                  </div>
                            <% } %>
                      <% } %>
                  </div>

                  <div class="popover__content notes">
                      <% notifications = notes.size(); %>
                      <div class="notification-box">
                          <p>You have <%=notifications%> new notes</p>
                      </div>

                      <% if(notifications != 0) { %>

                      <% for(Note note: notes){ %>

                      <div class="notification-box">
                          <p><strong><%=userDao.getUserById(note.getAuthorId()).getName()%></strong> has sent you a message!</p>
                          <form action="/removeNote" method="post">
                              <input type="hidden" name="NoteID" value="<%=note.getNoteId()%>">
                              <input type="hidden" name="referringPage" value="<%= request.getRequestURI() %>">
                              <p><%=note.getText()%></p>
                              <button>Remove Note</button>
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
               <% photoPath = "/images/" + user.getImagePath(); %>
               <a href="<%=profileURL%>">
                   <img src="<%=photoPath%>" alt="Go To Profile">
                   <span>Go To Profile</span>
               </a>
           </div>

           <div>
               <a href="/logout"><i class='bx bx-log-out-circle' ></i></a>
           </div>
    <% } %>

</header>
</body>
</html>
