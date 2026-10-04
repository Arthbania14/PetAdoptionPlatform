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

@WebServlet("/ViewPetsServlet")
public class ViewPetsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        PetDAO petDAO = new PetDAO();
        List<Pet> pets = petDAO.getAllPets();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Manage Pets</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f0f2f5;margin:0;padding:20px;}");
        out.println("h2{color:#2c3e50;}");
        out.println("table{width:100%;border-collapse:collapse;background:white;box-shadow:0 2px 8px rgba(0,0,0,0.1);}");
        out.println("th,td{padding:12px 15px;text-align:left;border-bottom:1px solid #ddd;}");
        out.println("th{background:#2c3e50;color:white;}");
        out.println("tr:hover{background:#f5f5f5;}");
        out.println(".btn{padding:6px 12px;border:none;border-radius:4px;color:white;text-decoration:none;font-size:13px;margin-right:5px;}");
        out.println(".approve{background:#27ae60;} .reject{background:#e74c3c;}");
        out.println("a.back{color:#3498db;text-decoration:none;display:inline-block;margin-bottom:15px;}");
        out.println("</style></head><body>");

        out.println("<a class='back' href='admin-dashboard.html'>← Back to Dashboard</a>");
        out.println("<h2>Manage Pet Listings</h2>");

        if (pets.isEmpty()) {
            out.println("<p>No pets found in the system yet.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Type</th><th>Breed</th><th>Age</th><th>Gender</th><th>Status</th><th>Action</th></tr>");

            for (Pet pet : pets) {
                out.println("<tr>");
                out.println("<td>" + pet.getPetId() + "</td>");
                out.println("<td>" + pet.getName() + "</td>");
                out.println("<td>" + pet.getType() + "</td>");
                out.println("<td>" + (pet.getBreed() != null ? pet.getBreed() : "-") + "</td>");
                out.println("<td>" + pet.getAge() + "</td>");
                out.println("<td>" + pet.getGender() + "</td>");
                out.println("<td><b>" + pet.getStatus() + "</b></td>");
                out.println("<td>");
                out.println("<a class='btn approve' href='UpdatePetStatusServlet?petId=" + pet.getPetId() + "&status=AVAILABLE'>Approve</a>");
                out.println("<a class='btn reject' href='UpdatePetStatusServlet?petId=" + pet.getPetId() + "&status=PENDING'>Set Pending</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</body></html>");
    }
}