package Servlets.UserHomePage;

import User.UserDAO;
import WebSockets.FriendsRequestWebSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SendId")
public class SendId extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String)request.getSession().getAttribute("userId");
        String type = request.getParameter("type");
        System.out.println("servlet says : " + id);
        try {
            if(type.equals("friends")) {
                FriendsRequestWebSocket.sendId(id);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
