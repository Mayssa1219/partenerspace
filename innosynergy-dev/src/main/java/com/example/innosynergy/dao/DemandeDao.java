package com.example.innosynergy.dao;
import com.example.innosynergy.model.DemandeData;
import com.example.innosynergy.model.Event;

import java.util.List;

public interface DemandeDao {
    void insertDemande(DemandeData demande);
    List<DemandeData> listDemandes();
    List<DemandeData> getDemandesByPartenaire(int idPartenaire);
    DemandeData findDemandeById(int idDemande);
    List<DemandeData> searchDemandes(String keyword);
    int getClientIdByName(String clientName);
    void updateDemande(DemandeData demande);
    void deleteDemande(int idDemande);
    List<Event> getEventTableData(int idPartenaire);
}