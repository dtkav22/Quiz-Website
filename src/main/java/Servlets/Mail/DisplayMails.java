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

@WebServlet("/inbox")
public class DisplayMails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String user_id = (String) session.getAttribute("userId");
        UserDAO dao = new UserDAO();
        try {
            ArrayList<Mail> mails = dao.getReceivedMailsForUser(user_id);
            System.out.println("here");
            session.setAttribute("mails", mails);
            request.getRequestDispatcher("jspFiles/displayMails.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

