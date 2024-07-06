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
        String user_id = (String) request.getSession().getAttribute("userID");
        UserDAO dao = new UserDAO();
        try {
            ArrayList<Mail> mails = dao.getReceivedMailsForUser(user_id);
            request.setAttribute("mails", mails);
            request.getRequestDispatcher("jspFiles/displayMails.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving your mails. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

