package Servlets.Mail;

import User.UserDAO;
import User.User;
import com.google.gson.JsonObject;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/sendMessage")
public class SendMessage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("here");
        HttpSession session = request.getSession(false);
        String user_id = (String) session.getAttribute("userId");
        System.out.println(user_id);
        String recipientUsername = request.getParameter("recipientUsername");
        System.out.println(recipientUsername);
        String mail_text = request.getParameter("mail_text");
        System.out.println(mail_text);
        UserDAO dao = new UserDAO();
        String receiver_id;
        try {
            receiver_id = dao.getUserId(recipientUsername);
            System.out.println(receiver_id);
            dao.sendMail(user_id, receiver_id, mail_text);
            System.out.println("Mail sent");
            request.setAttribute("successMessage", "Your mail has successfully been sent.");
            System.out.println("Here!");
            request.getRequestDispatcher("jspFiles/mail-sent.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "An error occurred while sending your message. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("here");
        request.getRequestDispatcher("jspFiles/newMessageForm.jsp").forward(request, response);
    }
}
