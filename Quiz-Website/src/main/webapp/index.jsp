<%--
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

    </style>

</head>
<body>

<header>
    <img class="logo" src="images/logo.png" alt="logo">

    <nav>
        <ul class="navbar_options">
            <li><a href = "test.jsp">Home</a></li>
            <li><a href = "test.jsp">Home</a></li>
        </ul>
    </nav>
    <div class="buttons">
    <a href="login.jsp"><button>Login</button></a>
    <a href="sign_up.jsp"><button>Sign Up</button></a>
    </div>
</header>
</body>
</html>
