package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AdminLayoutController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox sidebar;

    @FXML
    private ScrollPane scrollPane;

    private Button selectedButton; // Bouton actuellement sélectionné
    private boolean isSidebarVisible = true; // État de visibilité de la sidebar

    // Constantes de style pour les boutons de la sidebar
    private static final String DEFAULT_BUTTON_STYLE = "-fx-text-fill: #747374; -fx-background-color: transparent; -fx-font-size: 15px; -fx-pref-width: 220px; -fx-pref-height: 54px;";
    private static final String SELECTED_BUTTON_STYLE = "-fx-text-fill: #ffffff; -fx-background-color: #81A8C5; -fx-font-size: 15px; -fx-pref-width: 400px; -fx-pref-height: 25px; -fx-background-radius: 15px;";

    @FXML
    private void initialize() {
        // Initial setup if needed
    }

    @FXML
    private void updateContentBasedOnButton() {
        // Logic to update content based on the button clicked
    }

    @FXML
    private void toggleSidebarVisibility() {
        if (isSidebarVisible) {
            mainLayout.setLeft(null);
        } else {
            mainLayout.setLeft(new ScrollPane(sidebar));
        }
        isSidebarVisible = !isSidebarVisible;
    }

    // Other methods for handling button actions...
}