package com.example.innosynergy.dao;

import com.example.innosynergy.model.PartenaireData;

import java.util.List;

public interface PartenaireDao {
    void addPartenaire(PartenaireData partenaire);
    PartenaireData getPartenaire(int id);
    List<PartenaireData> getAllPartenaires();
    void updatePartenaire(PartenaireData user);
    void deletePartenaire(int id);
}