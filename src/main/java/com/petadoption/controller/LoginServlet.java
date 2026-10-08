package com.petadoption.controller;
import com.petadoption.util.StatsThread;
import com.petadoption.dao.UserDAO;
import com.petadoption.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("name", user.getName());
            session.setAttribute("role", user.getRole());

            // Start background stats thread
            new StatsThread().start();

            // Redirect based on role
            String role = user.getRole();

            if ("ADMIN".equals(role)) {
                response.sendRedirect("admin-dashboard.html");
            } else if ("SHELTER".equals(role)) {
                response.sendRedirect("shelter-dashboard.html");
            } else if ("ADOPTER".equals(role)) {
                response.sendRedirect("adopter-dashboard.html");
            } else {
                response.sendRedirect("index.html");
            }
        } else {
            // Login failed
            response.getWriter().println(
                    "<h3 style='color:red; text-align:center;'>" +
                            "Invalid Email or Password! <a href='login.html'>Try again</a></h3>"
            );
        }
    }
}
