package com.petadoption.controller;

import com.petadoption.dao.UserDAO;
import com.petadoption.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        UserDAO userDAO = new UserDAO();

        // Check if email already exists
        if (userDAO.isEmailExists(email)) {
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Email already registered! <a href='register.html'>Try again</a></h3>");
            return;
        }

        // Create User object
        User user = new User(name, email, password, role, phone);

        // Register user
        boolean success = userDAO.registerUser(user);

        if (success) {
            response.sendRedirect("login.html");
        } else {
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Registration failed! <a href='register.html'>Try again</a></h3>");
        }
    }
}