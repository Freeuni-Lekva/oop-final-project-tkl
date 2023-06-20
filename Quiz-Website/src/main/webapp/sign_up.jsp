<%--
  Created by IntelliJ IDEA.
  User: ddadi
  Date: 6/20/2023
  Time: 12:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>

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
            height: 530px;
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

        .container form button:hover{
            background-color: aquamarine;
        }


    </style>
</head>
<body>

<div class="container">
    <form action="SignUpServlet" method="post">
        <h1>Sign Up</h1>
        <div class="input-box">
            <i class='bx bx-user'></i> <label>User Name</label>
            <input type="text" placeholder="UserName" class="text-field" name="name" required>

        </div>
        <div class="input-box">
            <i class='bx bxs-user-detail' ></i> <label>First Name</label>
            <input type="text" placeholder="FirstName" class="text-field" name="realName" required>

        </div>
        <div class="input-box">
            <i class='bx bxs-user-detail' ></i> <label>Last Name</label>
            <input type="text" placeholder="LastName" class="text-field" name="realLastName" required>
        </div>
        <div class="input-box">
            <i class='bx bx-envelope' ></i> <label>Email (optional) </label>
            <input type="email" placeholder="Email" class="text-field" name="email">
        </div>
        <div class="input-box">
            <i class='bx bxs-lock-alt' ></i> <label>Password</label>
            <input type="password" placeholder="Password" class="text-field" name="password" required>
        </div>
        <button type="submit">Create Account</button>
    </form>
</div>




</body>
</html>
