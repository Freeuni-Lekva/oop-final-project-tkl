package Servlets;

import DAOinterfaces.UserDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // Implementation is not necessary
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        int result = userDao.tryToLogin(name, password);

        if(result == UserDao.ACCOUNT_NOT_FOUND){
            System.out.println("Account not found");
        }else if(result == UserDao.SUCCESSFULLY_LOGIN){
            System.out.println("Successfully Log in");
        }else if(result == UserDao.INCORRECT_PASSWORD){
            System.out.println("Incorrect Password");
        }

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
}
