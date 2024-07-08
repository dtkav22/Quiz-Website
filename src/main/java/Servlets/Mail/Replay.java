package Servlets.Mail;

import User.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/replay")
public class Replay extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String user_id = (String) session.getAttribute("userId");
        String receiver_id = (String) session.getAttribute("receiver_id");
        String headMail_id = (String) session.getAttribute("mailId");
        String mail_text = request.getParameter("mail_text");
        UserDAO dao = new UserDAO();
        try {
            if(receiver_id == null) {
                request.getRequestDispatcher("jspFiles/error.jsp").forward(request, response);
            }
            dao.sendMail(user_id, receiver_id, mail_text, null,headMail_id);
            request.getRequestDispatcher("jspFiles/mail-sent.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String mail_id = (String) session.getAttribute("mailId");
        UserDAO dao = new UserDAO();
        String receiver_id;
        String subject;
        try {
            receiver_id = dao.getMailById(mail_id).getSender_id();
            subject = dao.getMailById(mail_id).getMail_Subject();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute("receiver_id", receiver_id);
        session.setAttribute("subject", subject);
        request.getRequestDispatcher("jspFiles/replayMail.jsp").forward(request, response);
    }

}
