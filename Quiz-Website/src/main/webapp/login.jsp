<%@ page import="DAOinterfaces.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 6/20/2023
  Time: 12:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login</title>

        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

        <style>

            @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap');

            *{
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Roboto', sans-serif;
            }

            body{
                min-height: 100vh;
                background: darkslategrey;
                display: flex;
            }

            .container{
                margin: auto;
                width: 500px;
                height: 370px;
                max-height: 90%;
                max-width: 90%;
            }

            .container form{
                width: 100%;
                height: 100%;
                padding: 10px;
                background: mediumaquamarine;
                border-radius: 5px;
                box-shadow: 0 8px 16px black;
            }

            .container form h1{
                text-align: center;
                margin-bottom: 20px;
                color: white;
            }

            .container form p{
                text-align: center;
                margin-top: 0px;
                color: aquamarine;
            }

            .container form .text-field{
                width: 100%;
                height: 30px;
                background: white;
                border-radius: 4px;
                border: 1px solid silver;
                margin: 10px 0 18px 0;
                padding: 0 10px;
            }

            .container form button{
                    margin-left: 50%;
                transform: translateX(-50%);
                width: 120px;
                height: 60px;
                border: none;
                border-radius: 50px;
                cursor: pointer;
                transition: all 0.3s ease 0s;
                background: darkslategrey;
                color: white;
            }

            .container form i{
                color: white;
            }

            .container form button:hover{
                background-color: aquamarine;
            }

            .container label{
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <form action="LoginServlet" method="post">

                <%
                    if(request.getAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME) != null){
                        int message = Integer.parseInt(request.getAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME).toString());

                        if(message == UserDao.ACCOUNT_NOT_FOUND){
                            out.println("<p> Account With That Name Can't Found, Try Again</p>");
                        }else if(message == UserDao.INCORRECT_PASSWORD){
                            out.println("<p> Incorrect Password, Try Again</p> ");
                        }
                    }
                %>
                <h1>Log In</h1>
                <div class="input-box">
                    <i class='bx bx-user'></i> <label>User Name</label>
                    <input type="text" placeholder="UserName" class="text-field" name="name" required>

                </div>
                <div class="input-box">
                    <i class='bx bxs-lock-alt' ></i> <label>Password</label>
                    <input type="password" placeholder="Password" class="text-field" name="password" required>
                </div>
                <label>Don't Have Account? </label>
                <a href="signUp.jsp">Sign Up Now</a>
                <br><br><button type="submit">Log In</button>
            </form>
        </div>
    </body>
</html>
