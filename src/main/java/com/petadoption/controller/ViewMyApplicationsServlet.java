package com.petadoption.controller;

import com.petadoption.dao.ApplicationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ViewMyApplicationsServlet")
public class ViewMyApplicationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int adopterId = (int) session.getAttribute("userId");
        ApplicationDAO appDAO = new ApplicationDAO();
        List<String[]> applications = appDAO.getApplicationsByAdopter(adopterId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>My Applications</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;margin:0;padding:0;}");
        out.println(".navbar{background:#2980b9;color:white;padding:15px 40px;display:flex;justify-content:space-between;align-items:center;}");
        out.println(".navbar a{color:white;text-decoration:none;margin-left:20px;}");
        out.println(".container{max-width:1000px;margin:30px auto;padding:0 20px;}");
        out.println("h2{color:#2c3e50;margin-bottom:20px;}");
        out.println("table{width:100%;border-collapse:collapse;background:white;box-shadow:0 2px 8px rgba(0,0,0,0.1);}");
        out.println("th,td{padding:12px 15px;text-align:left;border-bottom:1px solid #ddd;}");
        out.println("th{background:#2980b9;color:white;}");
        out.println("tr:hover{background:#f5f5f5;}");
        out.println(".status{font-weight:bold;}");
        out.println(".PENDING{color:#f39c12;} .APPROVED{color:#27ae60;} .REJECTED{color:#e74c3c;}");
        out.println(".no-data{background:white;padding:40px;text-align:center;border-radius:10px;color:#777;}");
        out.println("</style></head><body>");

        out.println("<div class='navbar'>");
        out.println("<h2>My Applications</h2>");
        out.println("<div>");
        out.println("<a href='adopter-dashboard.html'>Dashboard</a>");
        out.println("<a href='BrowsePetsServlet'>Browse Pets</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</div></div>");

        out.println("<div class='container'>");
        out.println("<h2>Your Adoption Applications</h2>");

        if (applications.isEmpty()) {
            out.println("<div class='no-data'><h3>You have not applied for any pets yet.</h3>");
            out.println("<p><a href='BrowsePetsServlet' style='color:#2980b9;'>Browse Pets</a></p></div>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Pet Name</th><th>Type</th><th>Status</th><th>Applied On</th><th>Message</th></tr>");

            for (String[] app : applications) {
                out.println("<tr>");
                out.println("<td>" + app[0] + "</td>");
                out.println("<td>" + app[1] + "</td>");
                out.println("<td>" + app[2] + "</td>");
                out.println("<td class='status " + app[3] + "'>" + app[3] + "</td>");
                out.println("<td>" + app[4] + "</td>");
                out.println("<td>" + (app[5] != null ? app[5] : "-") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</div></body></html>");
    }
}