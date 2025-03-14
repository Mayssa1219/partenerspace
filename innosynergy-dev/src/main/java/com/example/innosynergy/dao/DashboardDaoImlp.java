package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Event;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DashboardDaoImlp implements DashboardDao{
    private ConnexionBD DBConnection;
    @Override
    public int getClientCount() {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS client_count FROM clients")) {

            if (resultSet.next()) {
                return resultSet.getInt("client_count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getPartnerCount() {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS partner_count FROM partenaires")) {

            if (resultSet.next()) {
                return resultSet.getInt("partner_count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getHelpRequestCount() {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS help_request_count FROM demandes_aide")) {

            if (resultSet.next()) {
                return resultSet.getInt("help_request_count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<XYChart.Data<Number, Number>> getLineChartData() {
        List<XYChart.Data<Number, Number>> data = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MONTH(date_don) AS mois, SUM(montant) AS montant_total FROM dons GROUP BY mois")) {

            while (resultSet.next()) {
                int month = resultSet.getInt("mois");
                double montantTotal = resultSet.getDouble("montant_total");
                data.add(new XYChart.Data<>(month, montantTotal));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Event> getEventTableData() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM evenements WHERE status = 'actif'")) {

            while (resultSet.next()) {
                String number = resultSet.getString("id_evenement");
                String title = resultSet.getString("titre");
                String description = resultSet.getString("description");
                String dateEvenement = resultSet.getString("date_evenement");
                String place = resultSet.getString("lieu");
                String partner = resultSet.getString("id_partenaire");
                String status = resultSet.getString("status");
                events.add(new Event(number, title, description, dateEvenement, place, partner, status));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}
