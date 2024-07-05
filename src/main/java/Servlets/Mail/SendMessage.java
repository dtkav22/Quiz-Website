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
        String user_id = (String) request.getSession().getAttribute("user_id");
        String recipientUsername = request.getParameter("recipientUsername");
        String mail_text = request.getParameter("mail_text");
        UserDAO dao = new UserDAO();
        String receiver_id;
        try {
            receiver_id = dao.getUserId(recipientUsername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            dao.sendMail(user_id, receiver_id, mail_text);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("successMessage", "Your mail has successfully been sent.");
        request.getRequestDispatcher("mail-sent.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse responses){
    }
}
