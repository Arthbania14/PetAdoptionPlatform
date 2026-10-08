package com.petadoption.dao;

import com.petadoption.model.User;
import java.util.List;

public interface UserDAOInterface {
    boolean registerUser(User user);
    User loginUser(String email, String password);
    boolean isEmailExists(String email);
    List<User> getAllUsers();
}