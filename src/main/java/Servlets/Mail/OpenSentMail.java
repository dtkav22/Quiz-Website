package Servlets.Mail;

import User.Mail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/openSentMail")
public class OpenSentMail extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String mail_id = request.getParameter("mailId");
        session.setAttribute("mailId", mail_id);
        ArrayList<Mail> replaySet = (ArrayList<Mail>) session.getAttribute("replaySet");
        session.setAttribute("replaySet", replaySet);
        request.getRequestDispatcher("jspFiles/openSentMail.jsp").forward(request, response);
    }
}