package Servlets;

import DAOinterfaces.UserDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

        String userName = request.getParameter("name");
        String realName = request.getParameter("realName");
        String realLastName = request.getParameter("realLastName");
        String password = request.getParameter("password");

        int result = userDao.register(userName, realName, realLastName, password);

        if(result == UserDao.ACCOUNT_CREATED){

            User user = userDao.getUserByName(userName);
            long id = user.getId();
            request.getSession().setAttribute("MainUserID", Long.toString(id));
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;

        }else if(result == UserDao.ACCOUNT_FOUND_BY_NAME) {
            request.setAttribute(UserDao.MESSAGE_ATTRIBUTE_NAME, result);
        }

        RequestDispatcher rd = request.getRequestDispatcher("signUp.jsp");
        rd.forward(request, response);
    }
}
