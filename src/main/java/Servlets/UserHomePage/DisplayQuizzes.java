package Servlets.UserHomePage;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class DisplayQuizzes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String id = request.getParameter("profile_id");
        System.out.println(id + ",");
        if(id == null) {
            id = (String) request.getSession().getAttribute("userId");
        }
        if(type == null) {
            type = "friends";
        }
        System.out.println(id);
        request.setAttribute("profile_id", id);
        request.setAttribute("type", type);
        System.out.println("here");
        if(type.equals("performance") || type.equals("friends")) {
            request.getRequestDispatcher("jspFiles/displayPerformances.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("jspFiles/displayQuizzes.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
