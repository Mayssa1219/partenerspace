package com.example.innosynergy.dao;

import com.example.innosynergy.model.Don;
import java.util.List;

public interface DonDao {
    void insertDon(Don don);
    List<Don> listDonsByPartenaire(int idPartenaire);
    String findClientNameById(int idClient);
    int findClientIdByName(String clientName);
    List<Don> searchDons(String keyword);
}