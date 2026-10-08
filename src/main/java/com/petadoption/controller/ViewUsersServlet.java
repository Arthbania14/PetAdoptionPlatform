package com.petadoption.controller;

import com.petadoption.dao.UserDAO;
import com.petadoption.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ViewUsersServlet")
public class ViewUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if admin is logged in
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Manage Users</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;margin:0;padding:20px;}");
        out.println("h2{color:#2c3e50;}");
        out.println("table{width:100%;border-collapse:collapse;background:white;box-shadow:0 2px 8px rgba(0,0,0,0.1);}");
        out.println("th,td{padding:12px 15px;text-align:left;border-bottom:1px solid #ddd;}");
        out.println("th{background:#2c3e50;color:white;}");
        out.println("tr:hover{background:#f5f5f5;}");
        out.println("a{color:#3498db;text-decoration:none;margin-right:15px;}");
        out.println("</style></head><body>");

        out.println("<a href='admin-dashboard.html'>← Back to Dashboard</a>");
        out.println("<h2>All Registered Users</h2>");

        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Phone</th></tr>");

        for (User user : users) {
            out.println("<tr>");
            out.println("<td>" + user.getUserId() + "</td>");
            out.println("<td>" + user.getName() + "</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("<td>" + user.getRole() + "</td>");
            out.println("<td>" + (user.getPhone() != null ? user.getPhone() : "-") + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}