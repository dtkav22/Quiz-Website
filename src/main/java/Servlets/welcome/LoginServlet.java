package Servlets.welcome;

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

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDAO dao = new UserDAO();
        try {
            String id = dao.getUserId(username);
            if(id == null) {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "User not found");
                response.getWriter().write(jsonResponse.toString());
            } else {
                User user = dao.getUser(id);
                if(!user.isPasswordCorrect(password)) {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("error", "Incorrect Password");
                    response.getWriter().write(jsonResponse.toString());
                } else {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("success", true);
                    response.getWriter().write(jsonResponse.toString());
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //response.setContentType("application/json");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null){
            response.sendRedirect("/user/userHomePage.jsp");
        } else {
            request.getRequestDispatcher("/welcome/welcome.jsp").forward(request, response);
        }
    }
}
