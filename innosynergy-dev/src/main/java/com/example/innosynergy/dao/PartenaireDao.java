package com.example.innosynergy.dao;

import com.example.innosynergy.model.PartenaireData;
import com.example.innosynergy.model.PartenaireProfileModel;
import com.example.innosynergy.model.User;

import java.util.List;

public interface PartenaireDao {
    void addPartenaire(PartenaireData partenaire);
    PartenaireData getPartenaire(int id);
    List<PartenaireData> getAllPartenaires();
    void updatePartenaire(PartenaireProfileModel partenaire);
    void deletePartenaire(int id);
    void updateProfile(User user, PartenaireData partenaireData);
    PartenaireData getPartenaireGenerale(int id);
    PartenaireProfileModel getPartenaireById(int id);
    PartenaireProfileModel getPartenaireByUserId(int userId);
    void updateAvatar(int partenaireId, String avatar);
}