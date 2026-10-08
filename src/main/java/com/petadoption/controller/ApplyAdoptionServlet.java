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

@WebServlet("/ApplyAdoptionServlet")
public class ApplyAdoptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String petId = request.getParameter("petId");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Apply for Adoption</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;display:flex;justify-content:center;align-items:center;height:100vh;margin:0;}");
        out.println(".container{background:white;padding:40px;border-radius:12px;box-shadow:0 4px 15px rgba(0,0,0,0.1);width:450px;}");
        out.println("h2{color:#2c3e50;margin-bottom:20px;text-align:center;}");
        out.println("textarea{width:100%;height:120px;padding:12px;border:1px solid #ccc;border-radius:6px;resize:none;font-size:15px;}");
        out.println("button{width:100%;padding:12px;background:#27ae60;color:white;border:none;border-radius:6px;font-size:16px;cursor:pointer;margin-top:15px;}");
        out.println("button:hover{background:#219150;}");
        out.println("a{display:block;text-align:center;margin-top:15px;color:#2980b9;text-decoration:none;}");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Apply for Adoption</h2>");
        out.println("<form method='post' action='ApplyAdoptionServlet'>");
        out.println("<input type='hidden' name='petId' value='" + petId + "'>");
        out.println("<label>Why do you want to adopt this pet?</label>");
        out.println("<textarea name='message' required placeholder='Write a short message...'></textarea>");
        out.println("<button type='submit'>Submit Application</button>");
        out.println("</form>");
        out.println("<a href='BrowsePetsServlet'>Cancel</a>");
        out.println("</div>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int petId = Integer.parseInt(request.getParameter("petId"));
        int adopterId = (int) session.getAttribute("userId");
        String message = request.getParameter("message");

        ApplicationDAO appDAO = new ApplicationDAO();
        boolean success = appDAO.applyForAdoption(petId, adopterId, message);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Application Status</title>");
        out.println("<style>body{font-family:Arial;text-align:center;padding-top:100px;background:#f0f2f5;}");
        out.println(".box{background:white;display:inline-block;padding:40px;border-radius:10px;box-shadow:0 3px 10px rgba(0,0,0,0.1);}");
        out.println("a{display:inline-block;margin-top:20px;padding:10px 20px;background:#2980b9;color:white;text-decoration:none;border-radius:5px;}</style></head><body>");

        out.println("<div class='box'>");
        if (success) {
            out.println("<h2 style='color:green;'>Application Submitted Successfully!</h2>");
            out.println("<p>Your adoption request has been sent to the shelter.</p>");
        } else {
            out.println("<h2 style='color:red;'>Application Failed</h2>");
            out.println("<p>Something went wrong. Please try again.</p>");
        }
        out.println("<a href='BrowsePetsServlet'>Back to Browse Pets</a>");
        out.println("</div></body></html>");
    }
}