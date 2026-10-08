package com.petadoption.controller;

import com.petadoption.dao.ApplicationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/UpdateApplicationStatusServlet")
public class UpdateApplicationStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"SHELTER".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        try {
            int appId = Integer.parseInt(request.getParameter("appId"));
            String status = request.getParameter("status");

            ApplicationDAO appDAO = new ApplicationDAO();
            appDAO.updateApplicationStatus(appId, status);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ViewShelterApplicationsServlet");
    }
}