package com.petadoption.controller;

import com.petadoption.dao.ApplicationDAO;
import com.petadoption.dao.PetDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ViewShelterApplicationsServlet")
public class ViewShelterApplicationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"SHELTER".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        PetDAO petDAO = new PetDAO();
        int shelterId = petDAO.getShelterIdByUserId(userId);

        ApplicationDAO appDAO = new ApplicationDAO();
        List<String[]> applications = appDAO.getApplicationsByShelter(shelterId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Adoption Applications</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;margin:0;padding:0;}");
        out.println(".navbar{background:#27ae60;color:white;padding:15px 40px;display:flex;justify-content:space-between;align-items:center;}");
        out.println(".navbar a{color:white;text-decoration:none;margin-left:20px;}");
        out.println(".container{max-width:1200px;margin:30px auto;padding:0 20px;}");
        out.println("h2{color:#2c3e50;margin-bottom:20px;}");
        out.println("table{width:100%;border-collapse:collapse;background:white;box-shadow:0 2px 8px rgba(0,0,0,0.1);}");
        out.println("th,td{padding:12px 15px;text-align:left;border-bottom:1px solid #ddd;}");
        out.println("th{background:#27ae60;color:white;}");
        out.println("tr:hover{background:#f5f5f5;}");
        out.println(".btn{padding:6px 12px;border:none;border-radius:4px;color:white;text-decoration:none;font-size:13px;margin-right:5px;}");
        out.println(".approve{background:#27ae60;} .reject{background:#e74c3c;}");
        out.println(".no-data{background:white;padding:40px;text-align:center;border-radius:10px;color:#777;}");
        out.println("</style></head><body>");

        out.println("<div class='navbar'>");
        out.println("<h2>Adoption Applications</h2>");
        out.println("<div>");
        out.println("<a href='shelter-dashboard.html'>Dashboard</a>");
        out.println("<a href='add-pet.html'>Add Pet</a>");
        out.println("<a href='ViewMyPetsServlet'>My Pets</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</div></div>");

        out.println("<div class='container'>");
        out.println("<h2>Applications Received</h2>");

        if (applications.isEmpty()) {
            out.println("<div class='no-data'><h3>No applications received yet.</h3></div>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Pet</th><th>Adopter</th><th>Email</th><th>Message</th><th>Status</th><th>Applied On</th><th>Action</th></tr>");

            for (String[] app : applications) {
                out.println("<tr>");
                out.println("<td>" + app[0] + "</td>");
                out.println("<td>" + app[1] + "</td>");
                out.println("<td>" + app[2] + "</td>");
                out.println("<td>" + app[3] + "</td>");
                out.println("<td>" + (app[4] != null ? app[4] : "-") + "</td>");
                out.println("<td><b>" + app[5] + "</b></td>");
                out.println("<td>" + app[6] + "</td>");
                out.println("<td>");
                if ("PENDING".equals(app[5])) {
                    out.println("<a class='btn approve' href='UpdateApplicationStatusServlet?appId=" + app[0] + "&status=APPROVED'>Approve</a>");
                    out.println("<a class='btn reject' href='UpdateApplicationStatusServlet?appId=" + app[0] + "&status=REJECTED'>Reject</a>");
                } else {
                    out.println("-");
                }
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</div></body></html>");
    }
}