package Servlets;

import DAOinterfaces.FriendRequestDao;
import DAOinterfaces.FriendsDao;
import DAOinterfaces.NotesDao;
import Objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SendNoteServlet", value = "/send_note")
public class SendNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ...
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the NotesDao instance from the servlet context using its attribute name.
        NotesDao notesDao = (NotesDao) request.getServletContext().getAttribute(NotesDao.ATTRIBUTE_NAME);

        // Retrieve the main user's ID from the session and convert it to a Long.
        String main_user_id = (String) request.getSession().getAttribute("MainUserID");
        Long mainId = Long.parseLong(main_user_id);

        // Retrieve the friend's ID from the request parameter and convert it to a Long.
        String frId = request.getParameter("friend_id");
        Long friendId = Long.parseLong(frId);

        // Retrieve the note text from the request parameter.
        String text = request.getParameter("noteText");

        // Call the sendNote method of the NotesDao to send a note from the main user to the friend.
        notesDao.sendNote(mainId, friendId, text);

        // Forward the request and response to the userFriends.jsp page to continue processing.
        RequestDispatcher rd = request.getRequestDispatcher("userFriends.jsp");
        rd.forward(request, response);
    }
}
