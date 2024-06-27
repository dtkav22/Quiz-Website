package Servlets;

import User.User;
import User.UserDAO;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();

        if(!email.contains("@")) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "Invalid format of the email field");
            response.getWriter().write(jsonResponse.toString());
        }
        else {
            try {
                if(username == null || username.length() <= 4) {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("error", "Username is too short");
                    response.getWriter().write(jsonResponse.toString());
                }else if(dao.getUserId(username) != null) {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("error", "Username already in use");
                    response.getWriter().write(jsonResponse.toString());
                } else if(password == null || password.length() <= 8) {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("error", "Password must have at least 8 characters");
                    response.getWriter().write(jsonResponse.toString());
                }
                else {
                    dao.addUser(new User(username, password, email));
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("password", password);
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("success", "");
                    response.getWriter().write(jsonResponse.toString());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
