package com.example.innosynergy.services;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.DemandeData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DemandeAideService {
    public List<DemandeData> getAllDemandes() {
        List<DemandeData> demandes = new ArrayList<>();
        String query = "SELECT id_demande, id_client, id_partenaire, type_aide, " +
                "description, montant_demande, date_demande, status, preuves FROM demandes_aide";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                demandes.add(new DemandeData(
                        rs.getInt("id_demande"),
                        rs.getInt("id_client"),
                        rs.getInt("id_partenaire"),
                        rs.getString("type_aide"),
                        rs.getString("description"),
                        rs.getDouble("montant_demande"),
                        rs.getString("date_demande"),
                        rs.getString("status"),
                        rs.getString("preuves")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return demandes;
    }
}
