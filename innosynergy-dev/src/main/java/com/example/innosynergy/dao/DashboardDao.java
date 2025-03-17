package com.example.innosynergy.dao;

import com.example.innosynergy.model.Event;
import javafx.scene.chart.XYChart;

import java.util.List;

public interface DashboardDao {
    int getClientCount(int idPartenaire);
    int getPartnerCount();
    int getEventCount(int idPartenaire);
    int getHelpRequestCountByPartenaire(int idPartenaire);
    List<XYChart.Data<Number, Number>> getLineChartData();
    List<Event> getEventTableData(int idPartenaire);
}
