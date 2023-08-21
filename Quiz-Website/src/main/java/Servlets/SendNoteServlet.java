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
        NotesDao notesDao = (NotesDao) request.getServletContext().getAttribute(NotesDao.ATTRIBUTE_NAME);

        String main_user_id = (String) request.getSession().getAttribute("MainUserID");
        Long mainId = Long.parseLong(main_user_id);

        String frId = request.getParameter("friend_id");
        Long friendId = Long.parseLong(frId);

        String text = request.getParameter("noteText");

        notesDao.sendNote(mainId, friendId, text);

        RequestDispatcher rd = request.getRequestDispatcher("userFriends.jsp");
        rd.forward(request, response);
    }
}
