package com.example.innosynergy.dao;

import com.example.innosynergy.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User authenticateUser(String email, String password);
    void sendPasswordResetEmail(String email);
    void initiateGoogleLogin(String googleLoginUrl);
    User authenticateSansHachage(String email, String password);
    boolean registerUser(User user);
    public boolean isEmailRegistered(String email);
    boolean updatePassword(String email, String newPassword); // Add this method
    String findNameByEmail(String email);
    boolean updatePasswordProfil(String email, String newPassword);
    User getUserById(int id);
  User getUserByEmail(String email);
}