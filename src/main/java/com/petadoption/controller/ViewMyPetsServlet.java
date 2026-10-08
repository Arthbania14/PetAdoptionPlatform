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

@WebServlet("/ViewMyPetsServlet")
public class ViewMyPetsServlet extends HttpServlet {

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
        List<Pet> pets = petDAO.getPetsByShelterId(shelterId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>My Pets</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;margin:0;padding:0;}");
        out.println(".navbar{background:#27ae60;color:white;padding:15px 40px;display:flex;justify-content:space-between;align-items:center;}");
        out.println(".navbar a{color:white;text-decoration:none;margin-left:20px;}");
        out.println(".container{max-width:1100px;margin:30px auto;padding:0 20px;}");
        out.println("h2{color:#2c3e50;margin-bottom:20px;}");
        out.println("table{width:100%;border-collapse:collapse;background:white;box-shadow:0 2px 8px rgba(0,0,0,0.1);}");
        out.println("th,td{padding:12px 15px;text-align:left;border-bottom:1px solid #ddd;}");
        out.println("th{background:#27ae60;color:white;}");
        out.println("tr:hover{background:#f5f5f5;}");
        out.println(".no-data{background:white;padding:40px;text-align:center;border-radius:10px;color:#777;}");
        out.println("</style></head><body>");

        out.println("<div class='navbar'>");
        out.println("<h2>My Pets</h2>");
        out.println("<div>");
        out.println("<a href='shelter-dashboard.html'>Dashboard</a>");
        out.println("<a href='add-pet.html'>Add Pet</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</div></div>");

        out.println("<div class='container'>");
        out.println("<h2>Pets Listed by You</h2>");

        if (pets.isEmpty()) {
            out.println("<div class='no-data'><h3>You have not added any pets yet.</h3>");
            out.println("<p><a href='add-pet.html' style='color:#27ae60;'>Add your first pet</a></p></div>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Type</th><th>Breed</th><th>Age</th><th>Gender</th><th>Status</th></tr>");

            for (Pet pet : pets) {
                out.println("<tr>");
                out.println("<td>" + pet.getPetId() + "</td>");
                out.println("<td>" + pet.getName() + "</td>");
                out.println("<td>" + pet.getType() + "</td>");
                out.println("<td>" + (pet.getBreed() != null ? pet.getBreed() : "-") + "</td>");
                out.println("<td>" + pet.getAge() + "</td>");
                out.println("<td>" + pet.getGender() + "</td>");
                out.println("<td><b>" + pet.getStatus() + "</b></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</div></body></html>");
    }
}