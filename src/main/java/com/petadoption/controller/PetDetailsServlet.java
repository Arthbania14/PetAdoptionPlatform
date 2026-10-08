package com.petadoption.controller;

import com.petadoption.dao.PetDAO;
import com.petadoption.model.Pet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PetDetailsServlet")
public class PetDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int petId = Integer.parseInt(request.getParameter("petId"));
        PetDAO petDAO = new PetDAO();
        Pet pet = petDAO.getPetById(petId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Pet Details</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;margin:0;padding:0;}");
        out.println(".navbar{background:#2980b9;color:white;padding:15px 40px;display:flex;justify-content:space-between;align-items:center;}");
        out.println(".navbar a{color:white;text-decoration:none;margin-left:20px;}");
        out.println(".container{max-width:800px;margin:40px auto;background:white;padding:40px;border-radius:12px;box-shadow:0 3px 15px rgba(0,0,0,0.1);}");
        out.println("h1{color:#2c3e50;margin-bottom:10px;}");
        out.println(".info{margin:20px 0;}");
        out.println(".info p{font-size:16px;margin:10px 0;color:#444;}");
        out.println(".info b{color:#2c3e50;}");
        out.println(".btn{display:inline-block;margin-top:25px;padding:12px 25px;background:#27ae60;color:white;text-decoration:none;border-radius:6px;font-size:16px;margin-right:15px;}");
        out.println(".btn-back{background:#7f8c8d;}");
        out.println(".btn:hover{opacity:0.9;}");
        out.println("</style></head><body>");

        out.println("<div class='navbar'>");
        out.println("<h2>Pet Details</h2>");
        out.println("<div>");
        out.println("<a href='BrowsePetsServlet'>Back to Browse</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</div></div>");

        if (pet == null) {
            out.println("<div class='container'><h2>Pet not found</h2></div>");
        } else {
            out.println("<div class='container'>");
            out.println("<h1>" + pet.getName() + "</h1>");
            out.println("<div class='info'>");
            out.println("<p><b>Type:</b> " + pet.getType() + "</p>");
            out.println("<p><b>Breed:</b> " + (pet.getBreed() != null ? pet.getBreed() : "Unknown") + "</p>");
            out.println("<p><b>Age:</b> " + pet.getAge() + " years</p>");
            out.println("<p><b>Gender:</b> " + pet.getGender() + "</p>");
            out.println("<p><b>Status:</b> " + pet.getStatus() + "</p>");
            out.println("<p><b>Description:</b><br>" + (pet.getDescription() != null ? pet.getDescription() : "No description available.") + "</p>");
            out.println("</div>");

            out.println("<a class='btn' href='ApplyAdoptionServlet?petId=" + pet.getPetId() + "'>Apply for Adoption</a>");
            out.println("<a class='btn btn-back' href='BrowsePetsServlet'>Back to Browse</a>");
            out.println("</div>");
        }

        out.println("</body></html>");
    }
}