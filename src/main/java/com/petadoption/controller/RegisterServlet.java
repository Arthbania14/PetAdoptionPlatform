package com.petadoption.controller;

import com.petadoption.dao.UserDAO;
import com.petadoption.model.User;
import com.petadoption.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        UserDAO userDAO = new UserDAO();

        if (userDAO.isEmailExists(email)) {
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Email already registered! <a href='register.html'>Try again</a></h3>");
            return;
        }

        User user = new User(name, email, password, role, phone);
        boolean success = userDAO.registerUser(user);

        if (success && "SHELTER".equals(role)) {
            // Also create entry in shelters table
            try (Connection con = DBConnection.getConnection()) {
                // Get the newly created user_id
                PreparedStatement ps = con.prepareStatement("SELECT user_id FROM users WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    PreparedStatement ps2 = con.prepareStatement(
                            "INSERT INTO shelters (user_id, shelter_name) VALUES (?, ?)");
                    ps2.setInt(1, userId);
                    ps2.setString(2, name + "'s Shelter");
                    ps2.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (success) {
            response.sendRedirect("login.html");
        } else {
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Registration failed! <a href='register.html'>Try again</a></h3>");
        }
    }
}