package Servlets;

import DAOinterfaces.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "searchProfileServlet", value = "/search")
public class searchProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // No implementation in the GET method for this servlet
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the UserDao instance from the ServletContext
        UserDao userDAO = (UserDao) request.getServletContext().getAttribute(UserDao.ATTRIBUTE_NAME);

        // Retrieve the search query string from the request parameters
        String str = (String) request.getParameter("searchUser");

        // Use the UserDao to search for users whose names match the search query
        // The search query is used in the SQL WHERE clause with a LIKE operator to find partial matches
        request.setAttribute("searchedUsers", userDAO.getUsers(" WHERE name like " + "\'%" + str +"%\'"));

        // Output the SQL query string for debugging purposes
        System.out.println("WHERE name like " + "\'%" + str +"%\'");

        // Forward the request and response to the search.jsp page to display the search results
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}
