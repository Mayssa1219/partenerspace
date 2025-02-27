package com.example.innosynergy.controller;

import com.example.innosynergy.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventController {
    private ObservableList<Event> eventDataList;

    public EventController() {
        eventDataList = FXCollections.observableArrayList();
        // Exemple d'ajout de données d'événement


            // Ajout de données d'événements
            eventDataList.add(new Event("1", "Conférence sur l'IA", "Description de la conférence sur l'IA", "2025-02-16 10:00:00", "Paris", "Partenaire A", "actif"));
            eventDataList.add(new Event("2", "Séminaire sur la Data", "Description du séminaire sur la Data", "2025-02-17 11:00:00", "Lyon", "Partenaire B", "annulé"));
            eventDataList.add(new Event("3", "Atelier de développement", "Description de l'atelier de développement", "2025-02-18 09:00:00", "Marseille", "Partenaire C", "terminé"));
            eventDataList.add(new Event("4", "Hackathon Tech", "Compétition de programmation sur 48h", "2025-02-20 09:00:00", "Toulouse", "Partenaire D", "actif"));
            eventDataList.add(new Event("5", "Conférence Cybersécurité", "Discussion sur les nouvelles menaces et protections", "2025-02-22 10:30:00", "Nice", "Partenaire E", "annulé"));
            eventDataList.add(new Event("6", "Workshop UX/UI", "Atelier sur les bonnes pratiques du design d'interface", "2025-02-24 15:00:00", "Lille", "Partenaire F", "actif"));
            eventDataList.add(new Event("7", "Salon de l’Innovation", "Présentation des dernières innovations technologiques", "2025-02-26 08:30:00", "Bordeaux", "Partenaire G", "actif"));

            // Ajout de plus d'événements
            eventDataList.add(new Event("8", "Journée de l'Open Source", "Conférences et ateliers sur l'open source", "2025-03-01 10:00:00", "Strasbourg", "Partenaire H", "actif"));
            eventDataList.add(new Event("9", "Formation DevOps", "Formation sur les outils DevOps et CI/CD", "2025-03-05 09:00:00", "Rennes", "Partenaire I", "terminé"));
            eventDataList.add(new Event("10", "Meetup Java", "Rencontre entre développeurs Java", "2025-03-07 18:30:00", "Nantes", "Partenaire J", "actif"));
            eventDataList.add(new Event("11", "Conférence Cloud Computing", "Présentation des nouvelles tendances du cloud", "2025-03-10 14:00:00", "Montpellier", "Partenaire K", "annulé"));
            eventDataList.add(new Event("12", "Séminaire Sécurité Informatique", "Sécurité des systèmes et protection des données", "2025-03-12 13:00:00", "Grenoble", "Partenaire L", "actif"));
            eventDataList.add(new Event("13", "Atelier Python", "Cours pratique sur le développement en Python", "2025-03-15 16:00:00", "Clermont-Ferrand", "Partenaire M", "actif"));
            eventDataList.add(new Event("14", "Startup Weekend", "Créer une startup en 54h", "2025-03-20 17:00:00", "Dijon", "Partenaire N", "actif"));
            eventDataList.add(new Event("15", "Web3 et Blockchain", "Exploration des opportunités du Web3", "2025-03-25 11:00:00", "Toulon", "Partenaire O", "actif"));
    }

    public ObservableList<Event> getEventDataList() {
        return eventDataList;
    }

    public void addEvent(Event event) {
        eventDataList.add(event);
    }

    public void removeEvent(Event event) {
        eventDataList.remove(event);
    }

    public void updateEvent(Event oldEvent, Event newEvent) {
        int index = eventDataList.indexOf(oldEvent);
        if (index != -1) {
            eventDataList.set(index, newEvent);
        }
    }
}