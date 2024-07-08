package Servlets.Mail;

import User.Mail;
import User.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/openSentMail")
public class OpenSentMail extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String mail_id = request.getParameter("mailId");
        session.setAttribute("mailId", mail_id);
        UserDAO dao = new UserDAO();
        ArrayList<Mail> replaySet;
        try {
            replaySet = dao.getReplaysForMail(mail_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute("replaySet", replaySet);
        request.getRequestDispatcher("jspFiles/openSentMail.jsp").forward(request, response);
    }
}