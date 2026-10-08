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

@WebServlet("/AddPetServlet")
public class AddPetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"SHELTER".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String breed = request.getParameter("breed");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String description = request.getParameter("description");

        PetDAO petDAO = new PetDAO();
        int shelterId = petDAO.getShelterIdByUserId(userId);

        if (shelterId == -1) {
            response.getWriter().println("<h3 style='color:red;text-align:center;'>Shelter not found. Please contact admin.</h3>");
            return;
        }

        Pet pet = new Pet();
        pet.setShelterId(shelterId);
        pet.setName(name);
        pet.setType(type);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setGender(gender);
        pet.setDescription(description);

        boolean success = petDAO.addPet(pet);

        if (success) {
            response.sendRedirect("shelter-dashboard.html");
        } else {
            response.getWriter().println("<h3 style='color:red;text-align:center;'>Failed to add pet. <a href='add-pet.html'>Try again</a></h3>");
        }
    }
}