package Servlets.Mail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/openMail")
public class OpenMail extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String mail_id = request.getParameter("mailId");
        session.setAttribute("mailId", mail_id);
        request.getRequestDispatcher("jspFiles/openMail.jsp").forward(request, response);
    }
}
