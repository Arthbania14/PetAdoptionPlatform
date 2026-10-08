package com.petadoption.controller;

import com.petadoption.dao.PetDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/UpdatePetStatusServlet")
public class UpdatePetStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        try {
            int petId = Integer.parseInt(request.getParameter("petId"));
            String status = request.getParameter("status");

            PetDAO petDAO = new PetDAO();
            petDAO.updatePetStatus(petId, status);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect back to pets list
        response.sendRedirect("ViewPetsServlet");
    }
}