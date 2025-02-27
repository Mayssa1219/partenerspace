package com.example.innosynergy.controller;

import com.example.innosynergy.model.Benevol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class BenevolController {
    private ObservableList<Benevol> benevolDataList;

    public BenevolController() {
        // Initialize the list with mock data or fetch from a database
        this.benevolDataList = FXCollections.observableArrayList(
                new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(2, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),
                new Benevol(3, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(4, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(5, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(6, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(7, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(8, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(9, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(10, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(11, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé"),   new Benevol(1, 1, "Bénévolat A", "Description A", LocalDateTime.now(), "ouvert"),
                new Benevol(12, 2, "Bénévolat B", "Description B", LocalDateTime.now(), "fermé")

        );
    }

    public ObservableList<Benevol> getBenevolDataList() {
        return benevolDataList;
    }

    public void addBenevol(Benevol benevol) {
        this.benevolDataList.add(benevol);
    }

    public void removeBenevol(Benevol benevol) {
        this.benevolDataList.remove(benevol);
    }

    public void updateBenevol(Benevol oldBenevol, Benevol newBenevol) {
        int index = this.benevolDataList.indexOf(oldBenevol);
        if (index != -1) {
            this.benevolDataList.set(index, newBenevol);
        }
    }
}