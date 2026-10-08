package com.petadoption.dao;

import com.petadoption.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // Apply for adoption
    public boolean applyForAdoption(int petId, int adopterId, String message) {
        String sql = "INSERT INTO adoption_applications (pet_id, adopter_id, message) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ps.setInt(2, adopterId);
            ps.setString(3, message);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all applications of a particular adopter
    public List<String[]> getApplicationsByAdopter(int adopterId) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT a.application_id, p.name AS pet_name, p.type, a.status, a.applied_at, a.message " +
                "FROM adoption_applications a " +
                "JOIN pets p ON a.pet_id = p.pet_id " +
                "WHERE a.adopter_id = ? ORDER BY a.applied_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adopterId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = new String[6];
                row[0] = String.valueOf(rs.getInt("application_id"));
                row[1] = rs.getString("pet_name");
                row[2] = rs.getString("type");
                row[3] = rs.getString("status");
                row[4] = rs.getString("applied_at");
                row[5] = rs.getString("message");
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get applications for a particular shelter
    public List<String[]> getApplicationsByShelter(int shelterId) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT a.application_id, p.name AS pet_name, u.name AS adopter_name, u.email, a.message, a.status, a.applied_at " +
                "FROM adoption_applications a " +
                "JOIN pets p ON a.pet_id = p.pet_id " +
                "JOIN users u ON a.adopter_id = u.user_id " +
                "WHERE p.shelter_id = ? ORDER BY a.applied_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, shelterId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = new String[7];
                row[0] = String.valueOf(rs.getInt("application_id"));
                row[1] = rs.getString("pet_name");
                row[2] = rs.getString("adopter_name");
                row[3] = rs.getString("email");
                row[4] = rs.getString("message");
                row[5] = rs.getString("status");
                row[6] = rs.getString("applied_at");
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update application status
    public boolean updateApplicationStatus(int applicationId, String status) {
        String sql = "UPDATE adoption_applications SET status = ? WHERE application_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, applicationId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}