package com.petadoption.util;

import com.petadoption.dao.PetDAO;
import com.petadoption.dao.ApplicationDAO;

public class StatsThread extends Thread {

    private static int totalPets = 0;
    private static int totalApplications = 0;

    // Synchronized method to update stats safely
    public static synchronized void updateStats(int pets, int applications) {
        totalPets = pets;
        totalApplications = applications;
        System.out.println("Stats Updated → Pets: " + totalPets + " | Applications: " + totalApplications);
    }

    public static int getTotalPets() {
        return totalPets;
    }

    public static int getTotalApplications() {
        return totalApplications;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Fetch latest counts every 30 seconds
                PetDAO petDAO = new PetDAO();
                int pets = petDAO.getAllPets().size();

                // For simplicity we are only counting pets here
                updateStats(pets, totalApplications);

                Thread.sleep(30000); // wait 30 seconds
            }
        } catch (InterruptedException e) {
            System.out.println("Stats Thread interrupted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}