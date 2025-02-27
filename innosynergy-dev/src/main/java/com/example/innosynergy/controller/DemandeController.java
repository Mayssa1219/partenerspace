package com.example.innosynergy.controller;

import com.example.innosynergy.model.DemandeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DemandeController {
    private ObservableList<DemandeData> demandeDataList;

    public DemandeController() {
        demandeDataList = FXCollections.observableArrayList();
        // Add sample data for demonstration purposes
        demandeDataList.add(new DemandeData("1", "John Doe", "123456789", "johndoe@example.com", "123 Main St", new java.util.Date(), "Acceptée", "Description 1", "http://example.com/file1.pdf"));
        demandeDataList.add(new DemandeData("2", "Jane Smith", "987654321", "janesmith@example.com", "456 Elm St", new java.util.Date(), "Refusée", "Description 2", "http://example.com/file2.pdf"));
        demandeDataList.add(new DemandeData("3", "Alice Johnson", "555555555", "alicejohnson@example.com", "789 Maple St", new java.util.Date(), "En attente", "Description 3", "http://example.com/file3.pdf"));
        demandeDataList.add(new DemandeData("1", "John Doe", "123456789", "johndoe@example.com", "123 Main St", new java.util.Date(), "Acceptée", "Description 1", "http://example.com/file1.pdf"));
        demandeDataList.add(new DemandeData("2", "Jane Smith", "987654321", "janesmith@example.com", "456 Elm St", new java.util.Date(), "Refusée", "Description 2", "http://example.com/file2.pdf"));
        demandeDataList.add(new DemandeData("3", "Alice Johnson", "555555555", "alicejohnson@example.com", "789 Maple St", new java.util.Date(), "En attente", "Description 3", "http://example.com/file3.pdf"));
        demandeDataList.add(new DemandeData("1", "John Doe", "123456789", "johndoe@example.com", "123 Main St", new java.util.Date(), "Acceptée", "Description 1", "http://example.com/file1.pdf"));
        demandeDataList.add(new DemandeData("2", "Jane Smith", "987654321", "janesmith@example.com", "456 Elm St", new java.util.Date(), "Refusée", "Description 2", "http://example.com/file2.pdf"));
        demandeDataList.add(new DemandeData("3", "Alice Johnson", "555555555", "alicejohnson@example.com", "789 Maple St", new java.util.Date(), "En attente", "Description 3", "http://example.com/file3.pdf"));
        demandeDataList.add(new DemandeData("1", "John Doe", "123456789", "johndoe@example.com", "123 Main St", new java.util.Date(), "Acceptée", "Description 1", "http://example.com/file1.pdf"));
        demandeDataList.add(new DemandeData("2", "Jane Smith", "987654321", "janesmith@example.com", "456 Elm St", new java.util.Date(), "Refusée", "Description 2", "http://example.com/file2.pdf"));
        demandeDataList.add(new DemandeData("3", "Alice Johnson", "555555555", "alicejohnson@example.com", "789 Maple St", new java.util.Date(), "En attente", "Description 3", "http://example.com/file3.pdf"));
        demandeDataList.add(new DemandeData("1", "John Doe", "123456789", "johndoe@example.com", "123 Main St", new java.util.Date(), "Acceptée", "Description 1", "http://example.com/file1.pdf"));
        demandeDataList.add(new DemandeData("2", "Jane Smith", "987654321", "janesmith@example.com", "456 Elm St", new java.util.Date(), "Refusée", "Description 2", "http://example.com/file2.pdf"));
        demandeDataList.add(new DemandeData("3", "Alice Johnson", "555555555", "alicejohnson@example.com", "789 Maple St", new java.util.Date(), "En attente", "Description 3", "http://example.com/file3.pdf"));
        demandeDataList.add(new DemandeData("1", "John Doe", "123456789", "johndoe@example.com", "123 Main St", new java.util.Date(), "Acceptée", "Description 1", "http://example.com/file1.pdf"));
        demandeDataList.add(new DemandeData("2", "Jane Smith", "987654321", "janesmith@example.com", "456 Elm St", new java.util.Date(), "Refusée", "Description 2", "http://example.com/file2.pdf"));
        demandeDataList.add(new DemandeData("3", "Alice Johnson", "555555555", "alicejohnson@example.com", "789 Maple St", new java.util.Date(), "En attente", "Description 3", "http://example.com/file3.pdf"));
    }

    public ObservableList<DemandeData> getDemandeDataList() {
        return demandeDataList;
    }

    public void addDemande(DemandeData demande) {
        demandeDataList.add(demande);
    }

    public void removeDemande(DemandeData demande) {
        demandeDataList.remove(demande);
    }

    public void updateDemande(DemandeData oldDemande, DemandeData newDemande) {
        int index = demandeDataList.indexOf(oldDemande);
        if (index != -1) {
            demandeDataList.set(index, newDemande);
        }
    }
}