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
import java.util.List;

@WebServlet("/BrowsePetsServlet")
public class BrowsePetsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        PetDAO petDAO = new PetDAO();
        List<Pet> pets = petDAO.getAvailablePets();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Browse Pets</title>");
        out.println("<style>");
        out.println("* { margin:0; padding:0; box-sizing:border-box; }");
        out.println("body { font-family: Arial, sans-serif; background:#f0f2f5; }");
        out.println(".navbar { background:#2980b9; color:white; padding:15px 40px; display:flex; justify-content:space-between; align-items:center; }");
        out.println(".navbar a { color:white; text-decoration:none; margin-left:20px; }");
        out.println(".container { max-width:1200px; margin:30px auto; padding:0 20px; }");
        out.println("h2 { color:#2c3e50; margin-bottom:25px; }");
        out.println(".pets-grid { display:grid; grid-template-columns:repeat(auto-fill, minmax(280px, 1fr)); gap:25px; }");
        out.println(".pet-card { background:white; border-radius:12px; overflow:hidden; box-shadow:0 3px 10px rgba(0,0,0,0.1); transition:0.3s; }");
        out.println(".pet-card:hover { transform:translateY(-5px); }");
        out.println(".pet-info { padding:20px; }");
        out.println(".pet-info h3 { color:#2c3e50; margin-bottom:8px; }");
        out.println(".pet-info p { color:#666; font-size:14px; margin-bottom:6px; }");
        out.println(".btn { display:inline-block; margin-top:12px; padding:8px 16px; background:#27ae60; color:white; text-decoration:none; border-radius:6px; font-size:14px; }");
        out.println(".btn:hover { background:#219150; }");
        out.println(".no-pets { background:white; padding:40px; text-align:center; border-radius:10px; color:#777; }");
        out.println("</style></head><body>");

        out.println("<div class='navbar'>");
        out.println("<h2>Browse Pets</h2>");
        out.println("<div>");
        out.println("<a href='adopter-dashboard.html'>Dashboard</a>");
        out.println("<a href='BrowsePetsServlet'>Browse Pets</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</div></div>");

        out.println("<div class='container'>");
        out.println("<h2>Available Pets for Adoption</h2>");

        if (pets.isEmpty()) {
            out.println("<div class='no-pets'><h3>No pets available right now.</h3><p>Please check back later.</p></div>");
        } else {
            out.println("<div class='pets-grid'>");
            for (Pet pet : pets) {
                out.println("<div class='pet-card'>");
                out.println("<div class='pet-info'>");
                out.println("<h3>" + pet.getName() + "</h3>");
                out.println("<p><b>Type:</b> " + pet.getType() + "</p>");
                out.println("<p><b>Breed:</b> " + (pet.getBreed() != null ? pet.getBreed() : "Unknown") + "</p>");
                out.println("<p><b>Age:</b> " + pet.getAge() + " years</p>");
                out.println("<p><b>Gender:</b> " + pet.getGender() + "</p>");
                out.println("<a class='btn' href='PetDetailsServlet?petId=" + pet.getPetId() + "'>View Details</a>");
                out.println("</div></div>");
            }
            out.println("</div>");
        }

        out.println("</div></body></html>");
    }
}
