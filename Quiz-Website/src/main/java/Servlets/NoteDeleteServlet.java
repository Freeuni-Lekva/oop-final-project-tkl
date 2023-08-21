package Servlets;

import DAOinterfaces.NotesDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "noteDeleteServlet", value = "/removeNote")
public class NoteDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        NotesDao notesDao = (NotesDao) request.getServletContext().getAttribute(NotesDao.ATTRIBUTE_NAME);
        String id = request.getParameter("NoteID");

        if(id != null) notesDao.delete_note(Long.parseLong(id));


        RequestDispatcher rd = request.getRequestDispatcher(request.getParameter("referringPage"));
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implementation is not necessary
    }
}
