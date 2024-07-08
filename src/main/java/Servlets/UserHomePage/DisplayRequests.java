package Servlets.UserHomePage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DisplayRequests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        if(type == null) {
            type = "friends";
        }
        if(type.equals("mails")) {
            request.getRequestDispatcher("jspFiles/mails.jsp").forward(request, response);
        } else if(type.equals("friends")){
            request.getRequestDispatcher("jspFiles/friendsRequests.jsp").forward(request, response);
        } else if(type.equals("challenges")){
            request.getRequestDispatcher("jspFiles/challenges.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
