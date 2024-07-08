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

@WebServlet("/searchSentMails")
public class SearchSentMails extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = request.getParameter("search_field");
        String currentUserId = (String) session.getAttribute("userId");
        UserDAO dao = new UserDAO();
        ArrayList<Mail> mails;
        try {
            mails = dao.getSentMailsForUserTo(currentUserId, dao.getUserId(username));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.setAttribute("search", mails);
        request.getRequestDispatcher("jspFiles/searchSentMails.jsp").forward(request, response);

    }
}