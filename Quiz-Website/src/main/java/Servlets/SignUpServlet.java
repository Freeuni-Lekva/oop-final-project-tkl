package Servlets;

import DAOinterfaces.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // Implementation is not necessary
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

        String user = request.getParameter("name");
        String realName = request.getParameter("realName");
        String realLastName = request.getParameter("realLastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        int result = userDao.register(user, realName, realLastName, email, password);

        if(result == UserDao.ACCOUNT_CREATED){
            System.out.println("account created");

        }else if(result == UserDao.ACCOUNT_FOUND_BY_NAME){
            request.setAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME, result);
        }

        RequestDispatcher rd = request.getRequestDispatcher("sign_up.jsp");
        rd.forward(request, response);

    }
}
