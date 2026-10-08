package com.petadoption.dao;

import com.petadoption.model.Pet;
import java.util.List;

public interface PetDAOInterface {
    List<Pet> getAllPets();
    List<Pet> getAvailablePets();
    Pet getPetById(int petId);
    boolean updatePetStatus(int petId, String status);
    boolean addPet(Pet pet);
    int getShelterIdByUserId(int userId);
    List<Pet> getPetsByShelterId(int shelterId);
}