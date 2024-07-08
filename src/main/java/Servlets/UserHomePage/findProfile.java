package Servlets.UserHomePage;

import User.UserDAO;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/findProfile")
public class findProfile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id;
        String fusername = request.getParameter("fusername");
        System.out.println("fusername: " + fusername);
        UserDAO userDAO = new UserDAO();
        try {
            id = userDAO.getUserId(fusername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonResponse = new JsonObject();
        if(id == null) {
            jsonResponse.addProperty("error", "Username wasn't found.");
        } else {
            jsonResponse.addProperty("profile_id", id);
        }
        response.getWriter().write(jsonResponse.toString());
    }
}
