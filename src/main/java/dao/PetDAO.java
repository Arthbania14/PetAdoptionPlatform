package com.petadoption.dao;

import com.petadoption.model.Pet;
import com.petadoption.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PetDAO implements PetDAOInterface {

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pets.add(mapPet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pets;
    }

    public List<Pet> getAvailablePets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets WHERE status = 'AVAILABLE' ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pets.add(mapPet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pets;
    }

    public Pet getPetById(int petId) {
        String sql = "SELECT * FROM pets WHERE pet_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapPet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePetStatus(int petId, String status) {
        String sql = "UPDATE pets SET status = ? WHERE pet_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, petId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPet(Pet pet) {
        String sql = "INSERT INTO pets (shelter_id, name, type, breed, age, gender, description, status) VALUES (?, ?, ?, ?, ?, ?, ?, 'AVAILABLE')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pet.getShelterId());
            ps.setString(2, pet.getName());
            ps.setString(3, pet.getType());
            ps.setString(4, pet.getBreed());
            ps.setInt(5, pet.getAge());
            ps.setString(6, pet.getGender());
            ps.setString(7, pet.getDescription());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getShelterIdByUserId(int userId) {
        String sql = "SELECT shelter_id FROM shelters WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("shelter_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Pet> getPetsByShelterId(int shelterId) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets WHERE shelter_id = ? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, shelterId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pets.add(mapPet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pets;
    }

    // Helper method to avoid code repetition
    private Pet mapPet(ResultSet rs) throws Exception {
        Pet pet = new Pet();
        pet.setPetId(rs.getInt("pet_id"));
        pet.setShelterId(rs.getInt("shelter_id"));
        pet.setName(rs.getString("name"));
        pet.setType(rs.getString("type"));
        pet.setBreed(rs.getString("breed"));
        pet.setAge(rs.getInt("age"));
        pet.setGender(rs.getString("gender"));
        pet.setDescription(rs.getString("description"));
        pet.setPhoto(rs.getString("photo"));
        pet.setStatus(rs.getString("status"));
        return pet;
    }
}